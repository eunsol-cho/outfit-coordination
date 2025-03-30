package org.esjo.outfitcoordination.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.esjo.outfitcoordination.domain.product.mapper.ProductCategoryEntityMapper;
import org.esjo.outfitcoordination.domain.product.model.ProductCategory;
import org.esjo.outfitcoordination.domain.product.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductCategoryDomainService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryEntityMapper mapper;

    public ProductCategory findCategoryByDisplayName(String categoryName) {
        return productCategoryRepository.findByDisplayName(categoryName)
                .map(mapper::toModel)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 이름입니다."));
    }
}
