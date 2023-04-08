package com.konkon.onlinestore.product.search.service.application.rest.converter.extensions;

import com.konkon.onlinestore.product.search.service.application.rest.converter.ReviewConverter;
import com.konkon.onlinestore.product.search.service.application.rest.model.ReviewResponse;
import com.konkon.onlinestore.product.search.service.domain.entity.Review;

import javax.enterprise.context.ApplicationScoped;

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
}
