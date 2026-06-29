package com.nextstack.airetail.company.mapper;

import com.nextstack.airetail.company.dto.request.CompanyRequest;
import com.nextstack.airetail.company.dto.response.CompanyResponse;
import com.nextstack.airetail.company.entity.Company;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * MapStruct mapper for {@link Company} entity and DTOs.
 */
@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface CompanyMapper {

    Company toEntity(CompanyRequest request);

    CompanyResponse toResponse(Company entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(CompanyRequest request, @MappingTarget Company entity);
}
