package org.esjo.outfitcoordination.api.util;

import org.esjo.outfitcoordination.domain.product.model.Product;
import org.esjo.outfitcoordination.domain.product.model.ProductCategory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductCategoryUtils {


    /**
     * 카테고리별로 상품을 묶는다.
     */
    public static Map<ProductCategory, List<Product>> groupProductsByCategory(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::category));
    }

    /**
     * 카테고리 displayOrder 순으로 정렬한다.
     */
    public static List<Map.Entry<ProductCategory, List<Product>>> sortByDisplayOrder(Map<ProductCategory, List<Product>> productsByCategory) {
        return productsByCategory.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().displayOrder()))
                .toList();
    }

}
