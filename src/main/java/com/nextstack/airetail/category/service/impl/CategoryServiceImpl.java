package com.nextstack.airetail.category.service.impl;

import com.nextstack.airetail.category.dto.request.CategoryPageRequest;
import com.nextstack.airetail.category.dto.request.CategoryRequest;
import com.nextstack.airetail.category.dto.response.CategoryResponse;
import com.nextstack.airetail.category.entity.Category;
import com.nextstack.airetail.category.mapper.CategoryMapper;
import com.nextstack.airetail.category.repository.CategoryRepository;
import com.nextstack.airetail.category.service.CategoryService;
import com.nextstack.airetail.category.specification.CategorySpecifications;
import com.nextstack.airetail.category.validator.CategoryValidator;
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
 * Implementation of {@link CategoryService}.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryValidator categoryValidator;

    /**
     * Creates the service with required dependencies.
     */
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CompanyRepository companyRepository,
                               CategoryMapper categoryMapper,
                               CategoryValidator categoryValidator) {
        this.categoryRepository = categoryRepository;
        this.companyRepository = companyRepository;
        this.categoryMapper = categoryMapper;
        this.categoryValidator = categoryValidator;
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        categoryValidator.validateCreate(request);
        Category category = categoryMapper.toEntity(request);
        category.setCompany(findCompany(request.getCompanyId()));
        category.setParent(findParentOptional(request.getParentId()));
        category.setActive(true);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        return categoryMapper.toResponse(findActiveById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CategoryResponse> getAll(PageRequestDto pageRequest) {
        CategoryPageRequest request = new CategoryPageRequest();
        request.setPage(pageRequest.getPage());
        request.setSize(pageRequest.getSize());
        request.setSortBy(pageRequest.getSortBy());
        request.setSortDirection(pageRequest.getSortDirection());
        return getAll(request);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CategoryResponse> getAll(CategoryPageRequest pageRequest) {
        Page<Category> page = categoryRepository.findAll(
                CategorySpecifications.fromRequest(pageRequest),
                pageRequest.toPageable());
        return PageResponseMapper.toPageResponse(page, categoryMapper::toResponse);
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = findActiveById(id);
        categoryValidator.validateUpdate(id, request);
        categoryMapper.updateEntity(request, category);
        category.setCompany(findCompany(request.getCompanyId()));
        category.setParent(findParentOptional(request.getParentId()));
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id) {
        Category category = findActiveById(id);
        category.setActive(false);
        categoryRepository.save(category);
    }

    private Category findActiveById(Long id) {
        return categoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    private Company findCompany(Long companyId) {
        return companyRepository.findByIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));
    }

    private Category findParentOptional(Long parentId) {
        if (parentId == null) {
            return null;
        }
        return categoryRepository.findByIdAndActiveTrue(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", parentId));
    }
}
