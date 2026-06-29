package com.nextstack.airetail.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextstack.airetail.AbstractIntegrationTest;
import com.nextstack.airetail.company.dto.request.CompanyRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for company REST endpoints.
 */
@AutoConfigureMockMvc
@EnabledIf("com.nextstack.airetail.TestConditions#isDockerAvailable")
class CompanyControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = "COMPANY_READ")
    void getAllCompanies_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/v1/companies").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(authorities = "COMPANY_WRITE")
    void createCompany_shouldReturnCreated() throws Exception {
        CompanyRequest request = new CompanyRequest();
        request.setName("Integration Test Co");
        request.setCode("INTTEST" + System.currentTimeMillis());

        mockMvc.perform(post("/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Integration Test Co"));
    }
}
