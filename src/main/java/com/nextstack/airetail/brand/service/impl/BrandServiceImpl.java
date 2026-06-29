package com.nextstack.airetail.brand.service.impl;

import com.nextstack.airetail.brand.dto.request.BrandPageRequest;
import com.nextstack.airetail.brand.dto.request.BrandRequest;
import com.nextstack.airetail.brand.dto.response.BrandResponse;
import com.nextstack.airetail.brand.entity.Brand;
import com.nextstack.airetail.brand.mapper.BrandMapper;
import com.nextstack.airetail.brand.repository.BrandRepository;
import com.nextstack.airetail.brand.service.BrandService;
import com.nextstack.airetail.brand.specification.BrandSpecifications;
import com.nextstack.airetail.brand.validator.BrandValidator;
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
 * Implementation of {@link BrandService}.
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final CompanyRepository companyRepository;
    private final BrandMapper brandMapper;
    private final BrandValidator brandValidator;

    /**
     * Creates the service with required dependencies.
     */
    public BrandServiceImpl(BrandRepository brandRepository,
                            CompanyRepository companyRepository,
                            BrandMapper brandMapper,
                            BrandValidator brandValidator) {
        this.brandRepository = brandRepository;
        this.companyRepository = companyRepository;
        this.brandMapper = brandMapper;
        this.brandValidator = brandValidator;
    }

    @Override
    public BrandResponse create(BrandRequest request) {
        brandValidator.validateCreate(request);
        Brand brand = brandMapper.toEntity(request);
        brand.setCompany(findCompany(request.getCompanyId()));
        brand.setActive(true);
        return brandMapper.toResponse(brandRepository.save(brand));
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponse getById(Long id) {
        return brandMapper.toResponse(findActiveById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BrandResponse> getAll(PageRequestDto pageRequest) {
        BrandPageRequest request = new BrandPageRequest();
        request.setPage(pageRequest.getPage());
        request.setSize(pageRequest.getSize());
        request.setSortBy(pageRequest.getSortBy());
        request.setSortDirection(pageRequest.getSortDirection());
        return getAll(request);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BrandResponse> getAll(BrandPageRequest pageRequest) {
        Page<Brand> page = brandRepository.findAll(
                BrandSpecifications.fromRequest(pageRequest),
                pageRequest.toPageable());
        return PageResponseMapper.toPageResponse(page, brandMapper::toResponse);
    }

    @Override
    public BrandResponse update(Long id, BrandRequest request) {
        Brand brand = findActiveById(id);
        brandValidator.validateUpdate(id, request);
        brandMapper.updateEntity(request, brand);
        brand.setCompany(findCompany(request.getCompanyId()));
        return brandMapper.toResponse(brandRepository.save(brand));
    }

    @Override
    public void delete(Long id) {
        Brand brand = findActiveById(id);
        brand.setActive(false);
        brandRepository.save(brand);
    }

    private Brand findActiveById(Long id) {
        return brandRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", id));
    }

    private Company findCompany(Long companyId) {
        return companyRepository.findByIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));
    }
}
