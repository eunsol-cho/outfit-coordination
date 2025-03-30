package org.esjo.outfitcoordination.api.product.service;

import lombok.RequiredArgsConstructor;
import org.esjo.outfitcoordination.api.product.controller.dto.ProductCreateRequest;
import org.esjo.outfitcoordination.api.product.controller.dto.ProductIdResponse;
import org.esjo.outfitcoordination.api.product.controller.dto.ProductResponse;
import org.esjo.outfitcoordination.api.product.controller.dto.ProductUpdateRequest;
import org.esjo.outfitcoordination.api.product.mapper.ProductMapper;
import org.esjo.outfitcoordination.domain.product.service.ProductDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDomainService productDomainService;
    private final ProductMapper productMapper;

    @Transactional
    public ProductIdResponse createProduct(Long brandId, ProductCreateRequest request) {
        var product = productMapper.toModel(request);
        return productMapper.toProductIdResponse(productDomainService.create(brandId, request.categoryId(), product));
    }

    @Transactional
    public void updateProduct(Long brandId, Long id, ProductUpdateRequest request) {
        var product = productMapper.toModel(request);
        productDomainService.update(brandId, id, request.categoryId() ,product);
    }

    @Transactional
    public void deleteProduct(Long brandId, Long id) {
        productDomainService.delete(brandId, id);
    }

    public List<ProductResponse> getProducts(Long brandId) {
        return productDomainService.findAllByBrandIdAndDeletedAtIsNull(brandId).stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
}
