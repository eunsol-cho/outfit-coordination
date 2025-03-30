package org.esjo.outfitcoordination.api.brand.controller.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "브랜드별 카테고리별 최저가격 정보")
@Builder(toBuilder = true)
public record BrandWiseLowestTotalPriceDto(
        @Schema(description = "브랜드명", example = "나이키")
        String brandName,

        @ArraySchema(arraySchema = @Schema(description = "카테고리별 가격 정보 목록"))
        List<CategoryPriceDto> categories,

        @Schema(description = "브랜드별 총 최저가격", example = "10,000")
        String totalPrice
) {}

