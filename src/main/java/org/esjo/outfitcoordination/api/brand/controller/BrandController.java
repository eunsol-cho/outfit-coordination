package org.esjo.outfitcoordination.api.brand.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.esjo.outfitcoordination.api.brand.controller.dto.BrandCreateRequest;
import org.esjo.outfitcoordination.api.brand.controller.dto.BrandIdResponse;
import org.esjo.outfitcoordination.api.brand.controller.dto.BrandUpdateRequest;
import org.esjo.outfitcoordination.api.brand.controller.dto.BrandWiseLowestTotalPriceResponse;
import org.esjo.outfitcoordination.api.brand.service.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "브랜드 API")
@RestController
@RequestMapping("v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService service;

    @Operation(summary = "(구현 2) 모든 카테고리 상품을 단일 브랜드 기준으로 최저가 구매 시 브랜드 및 가격 정보 조회")
    @GetMapping("/lowest-total-price")
    public BrandWiseLowestTotalPriceResponse getBrandWiseLowestTotalPrice() {
        return service.getBrandWiseLowestTotalPrice();
    }

    @Operation(summary = "(구현 4) 브랜드 추가 ")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BrandIdResponse createBrand(@Valid @RequestBody BrandCreateRequest request) {
        return service.createBrand(request);
    }

    @Operation(summary = "(구현 4) 브랜드 업데이트 ")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBrand(@PathVariable Long id, @RequestBody BrandUpdateRequest request) {
        service.updateBrand(id, request);
    }

    @Operation(summary = "(구현 4) 브랜드 삭제 ")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBrand(@PathVariable Long id) {
        service.deleteBrand(id);
    }
}
