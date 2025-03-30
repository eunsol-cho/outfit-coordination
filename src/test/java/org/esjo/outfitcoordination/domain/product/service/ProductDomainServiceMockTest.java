package org.esjo.outfitcoordination.domain.product.service;

import org.esjo.outfitcoordination.domain.product.entity.BrandEntity;
import org.esjo.outfitcoordination.domain.product.entity.ProductCategoryEntity;
import org.esjo.outfitcoordination.domain.product.entity.ProductEntity;
import org.esjo.outfitcoordination.domain.product.mapper.ProductEntityMapper;
import org.esjo.outfitcoordination.domain.product.model.Product;
import org.esjo.outfitcoordination.domain.product.repository.BrandRepository;
import org.esjo.outfitcoordination.domain.product.repository.ProductCategoryRepository;
import org.esjo.outfitcoordination.domain.product.repository.ProductQueryRepository;
import org.esjo.outfitcoordination.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ProductDomainServiceMockTest {

    private ProductRepository productRepository;
    private BrandRepository brandRepository;
    private ProductCategoryRepository productCategoryRepository;
    private ProductQueryRepository productQueryRepository;
    private ProductEntityMapper mapper;
    private ProductDomainService service;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        brandRepository = mock(BrandRepository.class);
        productCategoryRepository = mock(ProductCategoryRepository.class);
        productQueryRepository = mock(ProductQueryRepository.class);
        mapper = mock(ProductEntityMapper.class);

        service = new ProductDomainService(
                productRepository,
                brandRepository,
                productCategoryRepository,
                productQueryRepository,
                mapper
        );
    }

    @Test
    void findLowestPricePerCategory_shouldReturnProducts() {
        var entity = ProductEntity.builder().build();
        var model = Product.builder().build();

        when(productQueryRepository.findMinPriceProductsPerCategory())
                .thenReturn(List.of(entity));
        when(mapper.toModel(entity)).thenReturn(model);

        var result = service.findLowestPricePerCategory();

        assertThat(result).containsExactly(model);
    }

    @Test
    void findLowestPricePerCategory_shouldThrowIfEmpty() {
        when(productQueryRepository.findMinPriceProductsPerCategory())
                .thenReturn(List.of());

        assertThatThrownBy(() -> service.findLowestPricePerCategory())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void create_shouldReturnProductId() {
        var brandId = 1L;
        var categoryId = 2L;
        var product = Product.builder().build();

        var brand = BrandEntity.builder()
                .id(brandId)
                .name("브랜드A")
                .deletedAt(null)
                .build();

        var category = ProductCategoryEntity.builder()
                .id(categoryId)
                .code("OUTER")
                .displayName("아우터")
                .displayOrder(1)
                .deletedAt(null)
                .build();

        var entity = mock(ProductEntity.class);

        when(brandRepository.findByIdAndDeletedAtIsNull(brandId))
                .thenReturn(Optional.of(brand));
        when(productCategoryRepository.findByIdAndDeletedAtIsNull(categoryId))
                .thenReturn(Optional.of(category));
        when(mapper.toEntity(product)).thenReturn(entity);
        when(productRepository.save(entity)).thenReturn(entity);
        when(entity.getId()).thenReturn(100L);

        var result = service.create(brandId, categoryId, product);

        verify(entity).assignBrand(brand);
        verify(entity).changeCategory(category);
        assertThat(result).isEqualTo(100L);
    }

    @Test
    void update_shouldSucceed() {
        var productId = 1L;
        var brandId = 10L;
        var categoryId = 20L;
        var product = Product.builder().build();

        var entity = mock(ProductEntity.class);
        var brand = BrandEntity.builder()
                .id(brandId)
                .name("브랜드A")
                .deletedAt(null)
                .build();

        var category = ProductCategoryEntity.builder()
                .id(categoryId)
                .code("TOP")
                .displayName("상의")
                .displayOrder(1)
                .deletedAt(null)
                .build();

        when(productRepository.findByIdAndDeletedAtIsNull(productId))
                .thenReturn(Optional.of(entity));
        when(entity.getBrand()).thenReturn(brand);
        when(productCategoryRepository.findByIdAndDeletedAtIsNull(categoryId))
                .thenReturn(Optional.of(category));

        service.update(brandId, productId, categoryId, product);

        verify(mapper).merge(entity, product);
        verify(entity).changeCategory(category);
    }

    @Test
    void delete_shouldSetDeletedAt() {
        var productId = 1L;
        var brandId = 2L;

        var product = mock(ProductEntity.class);
        var brand = BrandEntity.builder()
                .id(brandId)
                .name("브랜드A")
                .deletedAt(null)
                .build();

        when(productRepository.findByIdAndDeletedAtIsNull(productId))
                .thenReturn(Optional.of(product));
        when(product.getBrand()).thenReturn(brand);
        doNothing().when(product).setDeletedAt(any(Instant.class));

        service.delete(brandId, productId);

        verify(product).setDeletedAt(any(Instant.class));
    }


    @Test
    void delete_shouldThrowIfWrongBrand() {
        var productId = 1L;

        var product = mock(ProductEntity.class);
        var wrongBrand = BrandEntity.builder()
                .id(999L)
                .name("다른브랜드")
                .deletedAt(null)
                .build();

        when(productRepository.findByIdAndDeletedAtIsNull(productId))
                .thenReturn(Optional.of(product));
        when(product.getBrand()).thenReturn(wrongBrand);

        assertThatThrownBy(() -> service.delete(1L, productId))
                .isInstanceOf(SecurityException.class)
                .hasMessageContaining("해당 브랜드에 속한 상품이 아닙니다.");
    }

}
