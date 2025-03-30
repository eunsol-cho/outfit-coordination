package org.esjo.outfitcoordination.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.esjo.outfitcoordination.domain.product.mapper.ProductCategoryEntityMapper;
import org.esjo.outfitcoordination.domain.product.model.ProductCategory;
import org.esjo.outfitcoordination.domain.product.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductCategoryDomainService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryEntityMapper mapper;

    public ProductCategory findCategoryByCode(String productCategoryCode) {
        return productCategoryRepository.findByCodeAndDeletedAtIsNull(productCategoryCode)
                .map(mapper::toModel)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 코드입니다."));
    }

    public List<ProductCategory> findAllByDeletedAtIsNull() {
        var categories = productCategoryRepository.findAllByDeletedAtIsNull();

        if (categories.isEmpty()) {
            throw new NoSuchElementException("카테고리가 존재하지 않습니다.");
        }

        return categories.stream()
                .map(mapper::toModel)
                .toList();
    }
}
