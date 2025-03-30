package org.esjo.outfitcoordination.domain.product.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.esjo.outfitcoordination.domain.product.entity.ProductCategoryEntity;
import org.esjo.outfitcoordination.domain.product.entity.ProductEntity;
import org.esjo.outfitcoordination.domain.product.model.Brand;
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
class BrandDomainServiceTest {

    @Autowired
    BrandDomainService brandDomainService;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository categoryRepository;

    @PersistenceContext
    EntityManager em;

    Brand brandModel;

    @BeforeEach
    void setUp() {
        brandModel = Brand.builder()
                .name("나이키")
                .build();
    }

    @Test
    @DisplayName("브랜드 생성 -> ID 반환")
    void create_shouldSaveBrand() {
        // when
        Long brandId = brandDomainService.create(brandModel);

        // then
        var saved = brandRepository.findById(brandId).orElseThrow();
        assertThat(saved.getName()).isEqualTo("나이키");
        assertThat(saved.getDeletedAt()).isNull();
    }

    @Test
    @DisplayName("브랜드 이름 업데이트")
    void update_shouldChangeBrandName() {
        // given
        var brandId = brandDomainService.create(brandModel);

        var updatedModel = Brand.builder()
                .name("아디다스")
                .build();

        // when
        brandDomainService.update(brandId, updatedModel);

        // then
        var updated = brandRepository.findById(brandId).orElseThrow();
        assertThat(updated.getName()).isEqualTo("아디다스");
    }

    @Test
    @DisplayName("브랜드 삭제 -> 브랜드 & 상품 soft delete")
    void delete_shouldSoftDeleteBrandAndProducts() {
        // given
        var brandId = brandDomainService.create(brandModel);

        var category = categoryRepository.save(
                ProductCategoryEntity.builder()
                        .code("TOP")
                        .displayName("상의")
                        .displayOrder(1)
                        .build()
        );

        var brand = brandRepository.findById(brandId).orElseThrow();

        var product = productRepository.save(
                ProductEntity.builder()
                        .name("에어 맥스 90")
                        .price(new BigDecimal("129000"))
                        .brand(brand)
                        .category(category)
                        .build()
        );

        // when
        brandDomainService.delete(brandId);

        em.flush();
        em.clear();

        // then
        var deletedBrand = brandRepository.findById(brandId).orElseThrow();
        assertThat(deletedBrand.getDeletedAt()).isNotNull();

        var deletedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertThat(deletedProduct.getDeletedAt()).isNotNull();
    }
}
