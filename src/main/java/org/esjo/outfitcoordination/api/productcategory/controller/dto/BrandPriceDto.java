package org.esjo.outfitcoordination.api.productcategory.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "브랜드별 최저가격 정보")
@Builder(toBuilder = true)
public record BrandPriceDto(
        @Schema(description = "브랜드 이름", example = "나이키")
        String brandName,
        @Schema(description = "브랜드 최저가격", example = "10,000")
        String price
) {
}
