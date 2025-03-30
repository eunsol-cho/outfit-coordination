package org.esjo.outfitcoordination.api.productcategory.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "상품 카테고리 응답")
@Builder(toBuilder = true)
public record ProductCategoryResponse(
        Long id,
        String productCategoryCode,
        String productCategoryName
) {
}
