package com.nextstack.airetail.company;

import com.nextstack.airetail.company.dto.request.CompanyRequest;
import com.nextstack.airetail.company.dto.response.CompanyResponse;
import com.nextstack.airetail.company.entity.Company;
import com.nextstack.airetail.company.mapper.CompanyMapper;
import com.nextstack.airetail.company.repository.CompanyRepository;
import com.nextstack.airetail.company.service.impl.CompanyServiceImpl;
import com.nextstack.airetail.company.validator.CompanyValidator;
import com.nextstack.airetail.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link CompanyServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyMapper companyMapper;

    private CompanyValidator companyValidator;
    private CompanyServiceImpl companyService;

    @BeforeEach
    void setUp() {
        companyValidator = new CompanyValidator(companyRepository);
        companyService = new CompanyServiceImpl(companyRepository, companyMapper, companyValidator);
    }

    @Test
    void create_shouldReturnCompanyResponse() {
        CompanyRequest request = new CompanyRequest();
        request.setName("Test Co");
        request.setCode("TEST");

        Company company = Company.builder().name("Test Co").code("TEST").build();
        company.setId(1L);
        company.setActive(true);
        CompanyResponse response = CompanyResponse.builder().id(1L).name("Test Co").code("TEST").build();

        when(companyRepository.existsByCode("TEST")).thenReturn(false);
        when(companyMapper.toEntity(request)).thenReturn(company);
        when(companyRepository.save(company)).thenReturn(company);
        when(companyMapper.toResponse(company)).thenReturn(response);

        CompanyResponse result = companyService.create(request);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test Co");
    }

    @Test
    void create_shouldThrowWhenValidationFails() {
        CompanyRequest request = new CompanyRequest();
        request.setCode("DUPLICATE");
        when(companyRepository.existsByCode("DUPLICATE")).thenReturn(true);

        assertThatThrownBy(() -> companyService.create(request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("DUPLICATE");
    }

    @Test
    void getById_shouldReturnCompany() {
        Company company = Company.builder().name("Test Co").code("TEST").build();
        company.setId(1L);
        company.setActive(true);
        CompanyResponse response = CompanyResponse.builder().id(1L).name("Test Co").build();

        when(companyRepository.findByIdAndActiveTrue(1L)).thenReturn(Optional.of(company));
        when(companyMapper.toResponse(company)).thenReturn(response);

        CompanyResponse result = companyService.getById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }
}
