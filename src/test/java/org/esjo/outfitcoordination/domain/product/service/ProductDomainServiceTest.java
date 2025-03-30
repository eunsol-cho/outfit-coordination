package org.esjo.outfitcoordination.domain.product.service;

import org.esjo.outfitcoordination.domain.product.entity.BrandEntity;
import org.esjo.outfitcoordination.domain.product.entity.ProductCategoryEntity;
import org.esjo.outfitcoordination.domain.product.entity.ProductEntity;
import org.esjo.outfitcoordination.domain.product.model.Product;
import org.esjo.outfitcoordination.domain.product.repository.BrandRepository;
import org.esjo.outfitcoordination.domain.product.repository.ProductCategoryRepository;
import org.esjo.outfitcoordination.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductDomainServiceTest {

    @Autowired
    private ProductDomainService productDomainService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @BeforeEach
    void clean() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        brandRepository.deleteAll();
    }


    @Test
    @DisplayName("상품을 생성 -> ID 반환")
    void create_shouldSaveProduct() {
        // given
        var brand = brandRepository.save(
                BrandEntity.builder()
                        .name("나이키")
                        .build()
        );

        var category = categoryRepository.save(
                ProductCategoryEntity.builder()
                        .code("OUTER_")
                        .displayName("아우터_")
                        .displayOrder(1)
                        .build()
        );

        var product = Product.builder()
                .name("에어맥스")
                .price(new BigDecimal("129000"))
                .build();

        // when
        var productId = productDomainService.create(brand.getId(), category.getId(), product);

        // then
        var saved = productRepository.findById(productId).orElseThrow();
        assertThat(saved.getName()).isEqualTo("에어맥스");
        assertThat(saved.getBrand().getId()).isEqualTo(brand.getId());
        assertThat(saved.getCategory().getId()).isEqualTo(category.getId());
    }

    @Test
    @DisplayName("상품을 삭제 -> deletedAt 업데이트")
    void delete_shouldSetDeletedAt() {
        // given
        var brand = brandRepository.save(
                BrandEntity.builder()
                        .name("아디다스")
                        .build()
        );

        var category = categoryRepository.save(
                ProductCategoryEntity.builder()
                        .code("BOTTOM")
                        .displayName("하의")
                        .displayOrder(2)
                        .build()
        );

        var product = productRepository.save(
                ProductEntity.builder()
                        .name("트레이닝 팬츠")
                        .price(new BigDecimal("69000"))
                        .brand(brand)
                        .category(category)
                        .build()
        );

        // when
        productDomainService.delete(brand.getId(), product.getId());

        // then
        var deleted = productRepository.findById(product.getId()).orElseThrow();
        assertThat(deleted.getDeletedAt()).isNotNull();
    }
}
