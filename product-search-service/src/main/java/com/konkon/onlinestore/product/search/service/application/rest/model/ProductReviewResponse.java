package com.konkon.onlinestore.product.search.service.application.rest.model;

import java.util.List;

public record ProductReviewResponse(
        ProductResponse product,
        List<ReviewResponse> reviews
) {
}
