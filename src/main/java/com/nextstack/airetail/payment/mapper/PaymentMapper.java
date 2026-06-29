package com.nextstack.airetail.payment.mapper;

import com.nextstack.airetail.payment.dto.request.PaymentRequest;
import com.nextstack.airetail.payment.dto.response.PaymentResponse;
import com.nextstack.airetail.payment.entity.Payment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * MapStruct mapper for {@link Payment} entity and DTOs.
 */
@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sale", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "active", ignore = true)
    Payment toEntity(PaymentRequest request);

    @Mapping(source = "sale.id", target = "saleId")
    @Mapping(source = "sale.saleNumber", target = "saleNumber")
    PaymentResponse toResponse(Payment entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "sale", ignore = true)
    void updateEntity(PaymentRequest request, @MappingTarget Payment entity);
}
