package com.nextstack.airetail.branch.service.impl;

import com.nextstack.airetail.branch.dto.request.BranchPageRequest;
import com.nextstack.airetail.branch.dto.request.BranchRequest;
import com.nextstack.airetail.branch.dto.response.BranchResponse;
import com.nextstack.airetail.branch.entity.Branch;
import com.nextstack.airetail.branch.mapper.BranchMapper;
import com.nextstack.airetail.branch.repository.BranchRepository;
import com.nextstack.airetail.branch.service.BranchService;
import com.nextstack.airetail.branch.specification.BranchSpecifications;
import com.nextstack.airetail.branch.validator.BranchValidator;
import com.nextstack.airetail.common.dto.PageRequestDto;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.PageResponseMapper;
import com.nextstack.airetail.company.entity.Company;
import com.nextstack.airetail.company.repository.CompanyRepository;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link BranchService}.
 */
@Service
@Transactional
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final CompanyRepository companyRepository;
    private final BranchMapper branchMapper;
    private final BranchValidator branchValidator;

    /**
     * Creates the service with required dependencies.
     */
    public BranchServiceImpl(BranchRepository branchRepository,
                             CompanyRepository companyRepository,
                             BranchMapper branchMapper,
                             BranchValidator branchValidator) {
        this.branchRepository = branchRepository;
        this.companyRepository = companyRepository;
        this.branchMapper = branchMapper;
        this.branchValidator = branchValidator;
    }

    @Override
    public BranchResponse create(BranchRequest request) {
        branchValidator.validateCreate(request);
        Branch branch = branchMapper.toEntity(request);
        branch.setCompany(findCompany(request.getCompanyId()));
        branch.setActive(true);
        return branchMapper.toResponse(branchRepository.save(branch));
    }

    @Override
    @Transactional(readOnly = true)
    public BranchResponse getById(Long id) {
        return branchMapper.toResponse(findActiveById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BranchResponse> getAll(PageRequestDto pageRequest) {
        BranchPageRequest request = new BranchPageRequest();
        request.setPage(pageRequest.getPage());
        request.setSize(pageRequest.getSize());
        request.setSortBy(pageRequest.getSortBy());
        request.setSortDirection(pageRequest.getSortDirection());
        return getAll(request);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BranchResponse> getAll(BranchPageRequest pageRequest) {
        Page<Branch> page = branchRepository.findAll(
                BranchSpecifications.fromRequest(pageRequest),
                pageRequest.toPageable());
        return PageResponseMapper.toPageResponse(page, branchMapper::toResponse);
    }

    @Override
    public BranchResponse update(Long id, BranchRequest request) {
        Branch branch = findActiveById(id);
        branchValidator.validateUpdate(id, request);
        branchMapper.updateEntity(request, branch);
        branch.setCompany(findCompany(request.getCompanyId()));
        return branchMapper.toResponse(branchRepository.save(branch));
    }

    @Override
    public void delete(Long id) {
        Branch branch = findActiveById(id);
        branch.setActive(false);
        branchRepository.save(branch);
    }

    private Branch findActiveById(Long id) {
        return branchRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", id));
    }

    private Company findCompany(Long companyId) {
        return companyRepository.findByIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));
    }
}
