package org.esjo.outfitcoordination.api.brand.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "브랜드별 카테고리별 가격 정보")
@Builder(toBuilder = true)
public record CategoryPriceDto(
        @Schema(description = "카테고리명", example = "상의")
        String productCategoryName,
        @Schema(description = "카테고리별 가격", example = "1,000")
        String price
) {}