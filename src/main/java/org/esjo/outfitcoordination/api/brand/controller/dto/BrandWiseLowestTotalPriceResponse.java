package org.esjo.outfitcoordination.api.brand.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "브랜드별 카테고리별 최저가격 정보를 담는 응답")
@Builder(toBuilder = true)
public record BrandWiseLowestTotalPriceResponse(
        BrandWiseLowestTotalPriceDto lowestTotalPrice
) {}

