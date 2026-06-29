package com.nextstack.airetail.company.service.impl;

import com.nextstack.airetail.common.dto.PageRequestDto;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.PageResponseMapper;
import com.nextstack.airetail.company.dto.request.CompanyPageRequest;
import com.nextstack.airetail.company.dto.request.CompanyRequest;
import com.nextstack.airetail.company.dto.response.CompanyResponse;
import com.nextstack.airetail.company.entity.Company;
import com.nextstack.airetail.company.mapper.CompanyMapper;
import com.nextstack.airetail.company.repository.CompanyRepository;
import com.nextstack.airetail.company.service.CompanyService;
import com.nextstack.airetail.company.specification.CompanySpecifications;
import com.nextstack.airetail.company.validator.CompanyValidator;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link CompanyService}.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final CompanyValidator companyValidator;

    /**
     * Creates the service with required dependencies.
     */
    public CompanyServiceImpl(CompanyRepository companyRepository,
                              CompanyMapper companyMapper,
                              CompanyValidator companyValidator) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.companyValidator = companyValidator;
    }

    @Override
    public CompanyResponse create(CompanyRequest request) {
        companyValidator.validateCreate(request);
        Company company = companyMapper.toEntity(request);
        company.setActive(true);
        return companyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyResponse getById(Long id) {
        return companyMapper.toResponse(findActiveById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CompanyResponse> getAll(PageRequestDto pageRequest) {
        CompanyPageRequest request = new CompanyPageRequest();
        request.setPage(pageRequest.getPage());
        request.setSize(pageRequest.getSize());
        request.setSortBy(pageRequest.getSortBy());
        request.setSortDirection(pageRequest.getSortDirection());
        return getAll(request);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CompanyResponse> getAll(CompanyPageRequest pageRequest) {
        Page<Company> page = companyRepository.findAll(
                CompanySpecifications.fromRequest(pageRequest),
                pageRequest.toPageable());
        return PageResponseMapper.toPageResponse(page, companyMapper::toResponse);
    }

    @Override
    public CompanyResponse update(Long id, CompanyRequest request) {
        Company company = findActiveById(id);
        companyValidator.validateUpdate(id, request);
        companyMapper.updateEntity(request, company);
        return companyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public void delete(Long id) {
        Company company = findActiveById(id);
        company.setActive(false);
        companyRepository.save(company);
    }

    private Company findActiveById(Long id) {
        return companyRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
    }
}
