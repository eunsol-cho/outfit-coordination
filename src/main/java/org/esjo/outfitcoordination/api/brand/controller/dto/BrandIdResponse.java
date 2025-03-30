package org.esjo.outfitcoordination.api.brand.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "브랜드 ID 응답")
@Builder(toBuilder = true)
public record BrandIdResponse(
        @Schema(description = "브랜드 ID", example = "1")
        Long id
) {
}
