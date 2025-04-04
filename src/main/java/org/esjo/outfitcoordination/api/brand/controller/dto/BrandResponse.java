package org.esjo.outfitcoordination.api.brand.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "브랜드 응답")
@Builder(toBuilder = true)
public record BrandResponse(
        @Schema(description = "브랜드 ID", example = "1")
        Long id,
        @Schema(description = "브랜드명", example = "나이키")
        String name
) {
}
