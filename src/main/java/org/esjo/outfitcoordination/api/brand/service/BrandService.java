package org.esjo.outfitcoordination.api.brand.service;

import lombok.RequiredArgsConstructor;
import org.esjo.outfitcoordination.api.brand.controller.dto.*;
import org.esjo.outfitcoordination.api.brand.mapper.BrandMapper;
import org.esjo.outfitcoordination.domain.policy.TieBreakPolicy;
import org.esjo.outfitcoordination.domain.product.model.Product;
import org.esjo.outfitcoordination.domain.product.model.ProductCategory;
import org.esjo.outfitcoordination.domain.product.service.BrandDomainService;
import org.esjo.outfitcoordination.domain.product.service.ProductDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.esjo.outfitcoordination.api.util.PriceFormatter.formatPrice;
import static org.esjo.outfitcoordination.api.util.ProductCategoryUtils.groupProductsByCategory;
import static org.esjo.outfitcoordination.api.util.ProductCategoryUtils.sortByDisplayOrder;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final ProductDomainService productDomainService;
    private final BrandDomainService brandDomainService;
    private final TieBreakPolicy tieBreakPolicy;

    private final BrandMapper brandMapper;

    // TODO.esjo 캐싱
    public BrandWiseLowestTotalPriceResponse getBrandWiseLowestTotalPrice() {
        var products = productDomainService.findLowestTotalPricePerBrand();
        return buildBrandWiseLowestTotalPriceResponse(products);
    }

    private BrandWiseLowestTotalPriceResponse buildBrandWiseLowestTotalPriceResponse(List<Product> products) {
        if (products.isEmpty()) {
            throw new IllegalStateException("최저가 브랜드 상품이 존재하지 않습니다.");
        }

        var brandName = products.getFirst().brand().name();

        var productsByCategory = groupProductsByCategory(products);
        var sortedEntries = sortByDisplayOrder(productsByCategory);

        List<CategoryPriceDto> categories = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Map.Entry<ProductCategory, List<Product>> entry : sortedEntries) {
            ProductCategory category = entry.getKey();
            List<Product> candidates = entry.getValue();

            var chosen = tieBreakPolicy.selectOneAmongTies(candidates);
            totalPrice = totalPrice.add(chosen.price());

            categories.add(CategoryPriceDto.builder()
                    .productCategoryName(category.displayName())
                    .price(formatPrice(chosen.price())).build()
            );
        }

        return BrandWiseLowestTotalPriceResponse.builder()
                .lowestTotalPrice(BrandWiseLowestTotalPriceDto
                        .builder()
                        .brandName(brandName)
                        .categories(categories)
                        .totalPrice(formatPrice(totalPrice))
                        .build())
                .build();
    }


    @Transactional
    public BrandIdResponse createBrand(BrandCreateRequest request) {
        var brand = brandMapper.toModel(request);
        return brandMapper.toBrandIdResponse(brandDomainService.create(brand));
    }

    @Transactional
    public void updateBrand(Long id, BrandUpdateRequest request) {
        var brand = brandMapper.toModel(request);
        brandDomainService.update(id, brand);
    }

    @Transactional
    public void deleteBrand(Long id) {
        brandDomainService.delete(id);
    }
}
