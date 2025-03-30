package org.esjo.outfitcoordination.api.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.esjo.outfitcoordination.api.product.controller.dto.ProductCreateRequest;
import org.esjo.outfitcoordination.api.product.controller.dto.ProductIdResponse;
import org.esjo.outfitcoordination.api.product.controller.dto.ProductUpdateRequest;
import org.esjo.outfitcoordination.api.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품 API")
@RestController
@RequestMapping("v1/brands/{brandId}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @Operation(summary = "(구현 4) 상품 추가 ")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductIdResponse createProduct(@PathVariable Long brandId, @Valid @RequestBody ProductCreateRequest request) {
        return service.createProduct(brandId, request);
    }

    @Operation(summary = "(구현 4) 상품 업데이트 ")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable Long brandId, @PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        service.updateProduct(brandId, id, request);
    }

    @Operation(summary = "(구현 4) 상품 삭제 ")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long brandId, @PathVariable Long id) {
        service.deleteProduct(brandId, id);
    }
}
