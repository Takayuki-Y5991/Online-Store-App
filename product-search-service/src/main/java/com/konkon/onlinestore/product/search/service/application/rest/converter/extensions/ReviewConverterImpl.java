package com.konkon.onlinestore.product.search.service.application.rest.converter.extensions;

import com.konkon.onlinestore.product.search.service.application.rest.converter.ReviewConverter;
import com.konkon.onlinestore.product.search.service.application.rest.model.request.CreateReviewRequest;
import com.konkon.onlinestore.product.search.service.application.rest.model.request.UpdateReviewRequest;
import com.konkon.onlinestore.product.search.service.application.rest.model.response.ReviewResponse;
import com.konkon.onlinestore.product.search.service.domain.entity.Review;
import com.konkon.onlinestore.product.search.service.domain.value.ReviewRate;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class ReviewConverterImpl implements ReviewConverter {
    @Override
    public ReviewResponse toResponse(Review domain) {
        return new ReviewResponse(
                domain.getId(),
                domain.getProductId(),
                domain.getAccountId(),
                domain.getRating().value(),
                domain.getTitle(),
                domain.getComment(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }

    @Override
    public Review toDomain(CreateReviewRequest request) {
        return Review.createBuild(
                request.productId(),
                request.accountId(),
                new ReviewRate(request.rating()),
                request.title(),
                request.comment()
        );
    }

    @Override
    public Review toDomain(UUID reviewId, UpdateReviewRequest request) {
        return Review.build(
                reviewId,
                request.productId(),
                request.accountId(),
                new ReviewRate(request.rating()),
                request.title(),
                request.comment(),
                request.createdAt(),
                request.updatedAt());
    }
}
