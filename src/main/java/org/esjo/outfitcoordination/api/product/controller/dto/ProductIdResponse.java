package org.esjo.outfitcoordination.api.product.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "상품 ID 응답")
@Builder(toBuilder = true)
public record ProductIdResponse(
        @Schema(description = "상품 ID", example = "1")
        Long id
) {
}
