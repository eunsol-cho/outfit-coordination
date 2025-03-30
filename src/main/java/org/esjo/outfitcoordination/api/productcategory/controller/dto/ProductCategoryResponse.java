package org.esjo.outfitcoordination.api.productcategory.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "상품 카테고리 응답")
@Builder(toBuilder = true)
public record ProductCategoryResponse(
        @Schema(description = "상품 카테고리 ID", example = "1")
        Long id,

        @Schema(description = "상품 카테고리 코드", example = "TOPS")
        String productCategoryCode,

        @Schema(description = "상품 카테고리 이름", example = "상의")
        String productCategoryName
) {
}
