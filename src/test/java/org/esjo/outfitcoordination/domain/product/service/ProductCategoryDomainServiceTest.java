package org.esjo.outfitcoordination.domain.product.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.esjo.outfitcoordination.domain.product.entity.ProductCategoryEntity;
import org.esjo.outfitcoordination.domain.product.model.ProductCategory;
import org.esjo.outfitcoordination.domain.product.repository.ProductCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductCategoryDomainServiceTest {

    @Autowired
    ProductCategoryDomainService categoryDomainService;

    @Autowired
    ProductCategoryRepository categoryRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("카테고리 코드로 조회 성공")
    void findCategoryByCode_shouldReturnCategory() {
        // given
        var saved = categoryRepository.save(
                ProductCategoryEntity.builder()
                        .code("TOPS_")
                        .displayName("상의_")
                        .displayOrder(1)
                        .build()
        );

        em.flush();
        em.clear();

        // when
        ProductCategory result = categoryDomainService.findCategoryByCode(saved.getCode());

        // then
        assertThat(result).isNotNull();
        assertThat(result.displayName()).isEqualTo(saved.getDisplayName());
        assertThat(result.code()).isEqualTo(saved.getCode());
    }

    @Test
    @DisplayName("없는 카테고리 코드 조회 시 예외 발생")
    void findCategoryByDisplayName_shouldThrowIfNotFound() {
        // when & then
        assertThatThrownBy(() -> categoryDomainService.findCategoryByCode("NON_EXISTENT"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 카테고리 코드");
    }
}
