package org.esjo.outfitcoordination.api.productcategory.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "카테고리별 최저가격 브랜드 정보를 담는 응답")
@Builder(toBuilder = true)
public record CategoryWisePriceRangeBrandResponse(
        String productCategoryName,
        BrandPriceDto lowestPriceBrand,
        BrandPriceDto highestPriceBrand
) {
}
