package com.konkon.onlinestore.product.search.service.domain.entity.aggregation;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.entity.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ProductReview {
    private Product product;
    private List<Review> reviews;

    public static ProductReview build(Product product, List<Review> reviews) {
        return ProductReview.builder()
                .product(product)
                .reviews(reviews)
                .build();
    }
}
