package com.konkon.onlinestore.product.search.service.application.rest.converter;

import com.konkon.onlinestore.product.search.service.application.rest.model.request.CreateReviewRequest;
import com.konkon.onlinestore.product.search.service.application.rest.model.request.UpdateReviewRequest;
import com.konkon.onlinestore.product.search.service.application.rest.model.response.ReviewResponse;
import com.konkon.onlinestore.product.search.service.domain.entity.Review;

import java.util.UUID;

public interface ReviewConverter {

    ReviewResponse toResponse(Review domain);

    Review toDomain(CreateReviewRequest request);

    Review toDomain(UUID reviewId, UpdateReviewRequest request);
}
