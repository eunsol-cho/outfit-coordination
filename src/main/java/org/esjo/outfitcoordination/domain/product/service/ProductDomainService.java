package org.esjo.outfitcoordination.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.esjo.outfitcoordination.domain.product.mapper.ProductEntityMapper;
import org.esjo.outfitcoordination.domain.product.model.Product;
import org.esjo.outfitcoordination.domain.product.repository.BrandRepository;
import org.esjo.outfitcoordination.domain.product.repository.ProductCategoryRepository;
import org.esjo.outfitcoordination.domain.product.repository.ProductQueryRepository;
import org.esjo.outfitcoordination.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductDomainService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductQueryRepository productQueryRepository;
    private final ProductEntityMapper mapper;

    public List<Product> findLowestPricePerCategory() {
        var products = productQueryRepository.findMinPriceProductsPerCategory();

        if (products.isEmpty()) {
            throw new NoSuchElementException("최저가 카테고리 상품이 존재하지 않습니다.");
        }

        return products.stream().map(mapper::toModel).toList();
    }

    public List<Product> findLowestTotalPricePerBrand() {
        var brand = productRepository.findCheapestBrandCoveringAllCategories()
                .orElseThrow(() -> new NoSuchElementException("최저가 브랜드가 존재하지 않습니다."));

        var products = productQueryRepository.findMinPriceProductsPerCategoryAndBrand(brand.getBrandId());

        if (products.isEmpty()) {
            throw new NoSuchElementException("브랜드에 해당하는 상품이 존재하지 않습니다. [" + brand.getBrandId() + "]");
        }

        return products.stream().map(mapper::toModel).toList();
    }

    public List<Product> findMinPriceProductsByCategoryId(Long categoryId) {
        var products = productQueryRepository.findMinPriceProductsByCategoryId(categoryId);

        if (products.isEmpty()) {
            throw new NoSuchElementException("카테고리에 해당하는 상품이 존재하지 않습니다. [" + categoryId + "]");
        }

        return products.stream().map(mapper::toModel).toList();
    }

    public List<Product> findMaxPriceProductsByCategoryId(Long categoryId) {
        var products = productQueryRepository.findMaxPriceProductsByCategoryId(categoryId);

        if (products.isEmpty()) {
            throw new NoSuchElementException("카테고리에 해당하는 상품이 존재하지 않습니다. [" + categoryId + "]");
        }

        return products.stream().map(mapper::toModel).toList();
    }

    @Transactional
    public Long create(Long brandId, Long categoryId, Product product) {
        var brand = brandRepository.findByIdAndDeletedAtIsNull(brandId)
                .orElseThrow(() -> new NoSuchElementException("브랜드를 찾을 수 없습니다: " + brandId));

        var category = productCategoryRepository.findByIdAndDeletedAtIsNull(categoryId)
                .orElseThrow(() -> new NoSuchElementException("상품 카테고리를 찾을 수 없습니다: " + categoryId));

        var entity = mapper.toEntity(product);
        entity.assignBrand(brand);
        entity.changeCategory(category);

        return productRepository.save(entity).getId();
    }

    @Transactional
    public void update(Long brandId, Long id, Long categoryId, Product product) {
        var entity = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다: " + id));

        if (!entity.getBrand().getId().equals(brandId)) {
            throw new SecurityException("해당 브랜드에 속한 상품이 아닙니다.");
        }

        var category = productCategoryRepository.findByIdAndDeletedAtIsNull(categoryId)
                .orElseThrow(() -> new NoSuchElementException("상품 카테고리를 찾을 수 없습니다: " + categoryId));

        mapper.merge(entity, product);
        entity.changeCategory(category);
    }

    @Transactional
    public void delete(Long brandId, Long id) {
        var product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다: " + id));

        if (!product.getBrand().getId().equals(brandId)) {
            throw new SecurityException("해당 브랜드에 속한 상품이 아닙니다.");
        }

        product.setDeletedAt(Instant.now());
    }

    public List<Product> findAllByBrandIdAndDeletedAtIsNull(Long brandId) {
        var brand = brandRepository.findByIdAndDeletedAtIsNull(brandId)
                .orElseThrow(() -> new NoSuchElementException("브랜드를 찾을 수 없습니다: " + brandId));

        // TODO.esjo 커서로 조회
        var products = productRepository.findAllByBrandIdAndDeletedAtIsNull(brand.getId());

        if (products.isEmpty()) {
            throw new NoSuchElementException("해당 브랜드에 속한 상품이 없습니다.");
        }

        return products.stream().map(mapper::toModel).toList();
    }
}
