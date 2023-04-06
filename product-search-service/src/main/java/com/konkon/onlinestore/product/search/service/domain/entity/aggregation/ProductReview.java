package com.konkon.onlinestore.product.search.service.domain.entity.aggregation;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class ProductReview {
    private Product product;
    private Set<Review> reviews;
}
