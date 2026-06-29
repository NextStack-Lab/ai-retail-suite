package com.nextstack.airetail.user.service.impl;

import com.nextstack.airetail.branch.entity.Branch;
import com.nextstack.airetail.branch.repository.BranchRepository;
import com.nextstack.airetail.common.dto.PageRequestDto;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.PageResponseMapper;
import com.nextstack.airetail.company.entity.Company;
import com.nextstack.airetail.company.repository.CompanyRepository;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import com.nextstack.airetail.role.entity.Role;
import com.nextstack.airetail.role.repository.RoleRepository;
import com.nextstack.airetail.user.dto.request.UserPageRequest;
import com.nextstack.airetail.user.dto.request.UserRequest;
import com.nextstack.airetail.user.dto.response.UserResponse;
import com.nextstack.airetail.user.entity.User;
import com.nextstack.airetail.user.mapper.UserMapper;
import com.nextstack.airetail.user.repository.UserRepository;
import com.nextstack.airetail.user.service.UserService;
import com.nextstack.airetail.user.specification.UserSpecifications;
import com.nextstack.airetail.user.validator.UserValidator;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link UserService}.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates the service with required dependencies.
     */
    public UserServiceImpl(UserRepository userRepository,
                           CompanyRepository companyRepository,
                           BranchRepository branchRepository,
                           RoleRepository roleRepository,
                           UserMapper userMapper,
                           UserValidator userValidator,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.branchRepository = branchRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse create(UserRequest request) {
        userValidator.validateCreate(request);
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCompany(findCompany(request.getCompanyId()));
        user.setBranch(findBranchOptional(request.getBranchId()));
        user.setRoles(resolveRoles(request.getRoleIds()));
        user.setActive(true);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        return userMapper.toResponse(findActiveById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getAll(PageRequestDto pageRequest) {
        UserPageRequest request = new UserPageRequest();
        request.setPage(pageRequest.getPage());
        request.setSize(pageRequest.getSize());
        request.setSortBy(pageRequest.getSortBy());
        request.setSortDirection(pageRequest.getSortDirection());
        return getAll(request);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getAll(UserPageRequest pageRequest) {
        Page<User> page = userRepository.findAll(
                UserSpecifications.fromRequest(pageRequest),
                pageRequest.toPageable());
        return PageResponseMapper.toPageResponse(page, userMapper::toResponse);
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        User user = findActiveById(id);
        userValidator.validateUpdate(id, request);
        userMapper.updateEntity(request, user);
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setCompany(findCompany(request.getCompanyId()));
        user.setBranch(findBranchOptional(request.getBranchId()));
        if (request.getRoleIds() != null) {
            user.setRoles(resolveRoles(request.getRoleIds()));
        }
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        User user = findActiveById(id);
        user.setActive(false);
        userRepository.save(user);
    }

    private User findActiveById(Long id) {
        return userRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    private Company findCompany(Long companyId) {
        return companyRepository.findByIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));
    }

    private Branch findBranchOptional(Long branchId) {
        if (branchId == null) {
            return null;
        }
        return branchRepository.findByIdAndActiveTrue(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", branchId));
    }

    private Set<Role> resolveRoles(Set<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return new HashSet<>();
        }
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = roleRepository.findByIdAndActiveTrue(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
            roles.add(role);
        }
        return roles;
    }
}
