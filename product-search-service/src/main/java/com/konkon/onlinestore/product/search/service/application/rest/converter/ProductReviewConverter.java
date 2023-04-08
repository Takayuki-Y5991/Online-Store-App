package com.konkon.onlinestore.product.search.service.application.rest.converter;

import com.konkon.onlinestore.product.search.service.application.rest.model.ProductReviewResponse;
import com.konkon.onlinestore.product.search.service.domain.entity.aggregation.ProductReview;

public interface ProductReviewConverter {

    ProductReviewResponse toResponse(
            ProductReview productReview
    );
}
