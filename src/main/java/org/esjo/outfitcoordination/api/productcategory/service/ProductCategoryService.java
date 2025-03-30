package org.esjo.outfitcoordination.api.productcategory.service;

import lombok.RequiredArgsConstructor;
import org.esjo.outfitcoordination.api.productcategory.controller.dto.CategoryWiseLowestPriceBrandDto;
import org.esjo.outfitcoordination.api.productcategory.controller.dto.CategoryWiseLowestPriceBrandResponse;
import org.esjo.outfitcoordination.api.productcategory.controller.dto.CategoryWisePriceRangeBrandResponse;
import org.esjo.outfitcoordination.api.productcategory.mapper.ProductCategoryMapper;
import org.esjo.outfitcoordination.domain.policy.TieBreakPolicy;
import org.esjo.outfitcoordination.domain.product.model.Product;
import org.esjo.outfitcoordination.domain.product.model.ProductCategory;
import org.esjo.outfitcoordination.domain.product.service.ProductCategoryDomainService;
import org.esjo.outfitcoordination.domain.product.service.ProductDomainService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.esjo.outfitcoordination.api.util.PriceFormatter.formatPrice;
import static org.esjo.outfitcoordination.api.util.ProductCategoryUtils.groupProductsByCategory;
import static org.esjo.outfitcoordination.api.util.ProductCategoryUtils.sortByDisplayOrder;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductDomainService productDomainService;
    private final ProductCategoryDomainService productCategoryDomainService;
    private final TieBreakPolicy tieBreakPolicy;
    private final ProductCategoryMapper productCategoryMapper;

    // TODO.esjo 캐싱
    public CategoryWiseLowestPriceBrandResponse getCategoryWiseLowestPriceBrand() {
        var products = productDomainService.findLowestPricePerCategory();

        var productsByCategory = groupProductsByCategory(products);
        var sortedEntries = sortByDisplayOrder(productsByCategory);

        return buildCategoryWiseLowestPriceBrand(sortedEntries);
    }


    public CategoryWisePriceRangeBrandResponse getCategoryWisePriceRangeBrands(String productCategoryCode) {
        var category = productCategoryDomainService.findCategoryByCode(productCategoryCode);
        var minPriceProducts = productDomainService.findMinPriceProductsByCategoryId(category.id());
        var maxPriceProducts = productDomainService.findMaxPriceProductsByCategoryId(category.id());

        var chosenMin = tieBreakPolicy.selectOneAmongTies(minPriceProducts);
        var chosenMax = tieBreakPolicy.selectOneAmongTies(maxPriceProducts);

        var lowestPriceBrand = productCategoryMapper.toBrandPriceDto(chosenMin);
        var highestPriceBrand = productCategoryMapper.toBrandPriceDto(chosenMax);

        return CategoryWisePriceRangeBrandResponse.builder()
                .productCategoryName(category.displayName())
                .lowestPriceBrand(lowestPriceBrand)
                .highestPriceBrand(highestPriceBrand)
                .build();
    }

    /**
     * TieBreakPolicy로 최저가 상품을 선택하고,
     * 해당 상품을 DTO로 변환하면서 총액도 계산한다.
     */
    private CategoryWiseLowestPriceBrandResponse buildCategoryWiseLowestPriceBrand(
            List<Map.Entry<ProductCategory, List<Product>>> sortedEntries
    ) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<CategoryWiseLowestPriceBrandDto> categories = new ArrayList<>();

        for (Map.Entry<ProductCategory, List<Product>> entry : sortedEntries) {
            ProductCategory category = entry.getKey();
            List<Product> candidates = entry.getValue();

            var chosen = tieBreakPolicy.selectOneAmongTies(candidates);

            totalPrice = totalPrice.add(chosen.price());

            categories.add(
                    productCategoryMapper.toCategoryWiseLowestPriceBrandDto(
                            category.displayName(),
                            chosen
                    ));
        }

        return CategoryWiseLowestPriceBrandResponse
                .builder()
                .items(categories)
                .totalPrice(formatPrice(totalPrice))
                .build();
    }
}
