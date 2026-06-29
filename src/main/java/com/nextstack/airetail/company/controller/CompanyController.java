package com.nextstack.airetail.company.controller;

import com.nextstack.airetail.common.dto.ApiResponse;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.company.dto.request.CompanyPageRequest;
import com.nextstack.airetail.company.dto.request.CompanyRequest;
import com.nextstack.airetail.company.dto.response.CompanyResponse;
import com.nextstack.airetail.company.service.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for company management.
 */
@RestController
@RequestMapping("/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    /**
     * Creates the controller with company service dependency.
     *
     * @param companyService company business logic
     */
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Creates a new company.
     *
     * @param request     company data
     * @param httpRequest HTTP request for path logging
     * @return created company
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('COMPANY_WRITE')")
    public ApiResponse<CompanyResponse> create(@Valid @RequestBody CompanyRequest request,
                                               HttpServletRequest httpRequest) {
        CompanyResponse response = companyService.create(request);
        return ApiResponse.success("Company created successfully", response, httpRequest.getRequestURI());
    }

    /**
     * Returns a company by identifier.
     *
     * @param id          company identifier
     * @param httpRequest HTTP request for path logging
     * @return company details
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('COMPANY_READ')")
    public ApiResponse<CompanyResponse> getById(@PathVariable Long id,
                                                HttpServletRequest httpRequest) {
        return ApiResponse.success("Company retrieved successfully",
                companyService.getById(id), httpRequest.getRequestURI());
    }

    /**
     * Returns paginated companies with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @param httpRequest HTTP request for path logging
     * @return paginated companies
     */
    @GetMapping
    @PreAuthorize("hasAuthority('COMPANY_READ')")
    public ApiResponse<PageResponse<CompanyResponse>> getAll(CompanyPageRequest pageRequest,
                                                             HttpServletRequest httpRequest) {
        return ApiResponse.success("Companies retrieved successfully",
                companyService.getAll(pageRequest), httpRequest.getRequestURI());
    }

    /**
     * Updates an existing company.
     *
     * @param id          company identifier
     * @param request     updated company data
     * @param httpRequest HTTP request for path logging
     * @return updated company
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('COMPANY_WRITE')")
    public ApiResponse<CompanyResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody CompanyRequest request,
                                               HttpServletRequest httpRequest) {
        return ApiResponse.success("Company updated successfully",
                companyService.update(id, request), httpRequest.getRequestURI());
    }

    /**
     * Soft-deletes a company.
     *
     * @param id          company identifier
     * @param httpRequest HTTP request for path logging
     * @return success response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('COMPANY_WRITE')")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
        companyService.delete(id);
        return ApiResponse.success("Company deleted successfully", httpRequest.getRequestURI());
    }
}
