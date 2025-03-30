package org.esjo.outfitcoordination.api.productcategory.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "브랜드별 최저가격 정보를 담는 DTO")
@Builder(toBuilder = true)
public record BrandPriceDto(
        String brandName,
        String price
) {
}
