package com.nextstack.airetail.sales.mapper;

import com.nextstack.airetail.sales.dto.response.SaleItemResponse;
import com.nextstack.airetail.sales.dto.response.SaleResponse;
import com.nextstack.airetail.sales.entity.Sale;
import com.nextstack.airetail.sales.entity.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * MapStruct mapper for {@link Sale} entity and DTOs.
 */
@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface SaleMapper {

    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "branch.name", target = "branchName")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "cashier.id", target = "cashierId")
    @Mapping(source = "cashier.username", target = "cashierUsername")
    @Mapping(source = "items", target = "items")
    SaleResponse toResponse(Sale entity);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.sku", target = "productSku")
    @Mapping(source = "product.name", target = "productName")
    SaleItemResponse toItemResponse(SaleItem item);

    List<SaleItemResponse> toItemResponses(List<SaleItem> items);
}
