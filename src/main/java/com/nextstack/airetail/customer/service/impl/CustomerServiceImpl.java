package com.nextstack.airetail.customer.service.impl;

import com.nextstack.airetail.common.dto.PageRequestDto;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.PageResponseMapper;
import com.nextstack.airetail.company.entity.Company;
import com.nextstack.airetail.company.repository.CompanyRepository;
import com.nextstack.airetail.customer.dto.request.CustomerPageRequest;
import com.nextstack.airetail.customer.dto.request.CustomerRequest;
import com.nextstack.airetail.customer.dto.response.CustomerResponse;
import com.nextstack.airetail.customer.entity.Customer;
import com.nextstack.airetail.customer.mapper.CustomerMapper;
import com.nextstack.airetail.customer.repository.CustomerRepository;
import com.nextstack.airetail.customer.service.CustomerService;
import com.nextstack.airetail.customer.specification.CustomerSpecifications;
import com.nextstack.airetail.customer.validator.CustomerValidator;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link CustomerService}.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final CustomerMapper customerMapper;
    private final CustomerValidator customerValidator;

    /**
     * Creates the service with required dependencies.
     */
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CompanyRepository companyRepository,
                               CustomerMapper customerMapper,
                               CustomerValidator customerValidator) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.customerMapper = customerMapper;
        this.customerValidator = customerValidator;
    }

    @Override
    public CustomerResponse create(CustomerRequest request) {
        customerValidator.validateCreate(request);
        Customer customer = customerMapper.toEntity(request);
        customer.setCompany(findCompany(request.getCompanyId()));
        if (customer.getLoyaltyPoints() == null) {
            customer.setLoyaltyPoints(0);
        }
        customer.setActive(true);
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getById(Long id) {
        return customerMapper.toResponse(findActiveById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CustomerResponse> getAll(PageRequestDto pageRequest) {
        CustomerPageRequest request = new CustomerPageRequest();
        request.setPage(pageRequest.getPage());
        request.setSize(pageRequest.getSize());
        request.setSortBy(pageRequest.getSortBy());
        request.setSortDirection(pageRequest.getSortDirection());
        return getAll(request);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CustomerResponse> getAll(CustomerPageRequest pageRequest) {
        Page<Customer> page = customerRepository.findAll(
                CustomerSpecifications.fromRequest(pageRequest),
                pageRequest.toPageable());
        return PageResponseMapper.toPageResponse(page, customerMapper::toResponse);
    }

    @Override
    public CustomerResponse update(Long id, CustomerRequest request) {
        Customer customer = findActiveById(id);
        customerValidator.validateUpdate(id, request);
        customerMapper.updateEntity(request, customer);
        customer.setCompany(findCompany(request.getCompanyId()));
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public void delete(Long id) {
        Customer customer = findActiveById(id);
        customer.setActive(false);
        customerRepository.save(customer);
    }

    private Customer findActiveById(Long id) {
        return customerRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
    }

    private Company findCompany(Long companyId) {
        return companyRepository.findByIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));
    }
}
