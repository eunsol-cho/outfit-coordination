package org.esjo.outfitcoordination.api.brand.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Schema(description = "브랜드 수정 요청")
@Builder(toBuilder = true)
public record BrandUpdateRequest(
        @NotBlank(message = "브랜드명을 입력해주세요.")
        @Size(max = 100, message = "브랜드명은 100자 이하로 입력해주세요.")
        @Schema(description = "브랜드명", example = "나이키")
        String name
) {

}
