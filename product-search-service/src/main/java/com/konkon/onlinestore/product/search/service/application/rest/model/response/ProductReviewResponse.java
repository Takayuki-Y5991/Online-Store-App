package com.konkon.onlinestore.product.search.service.application.rest.model.response;

import java.util.List;

public record ProductReviewResponse(
        ProductResponse product,
        List<ReviewResponse> reviews
) {
}
