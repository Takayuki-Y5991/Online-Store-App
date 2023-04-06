package com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Review;
import com.konkon.onlinestore.product.search.service.domain.value.ReviewRate;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.ReviewEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator.ReviewTranslator;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReviewTranslatorImpl implements ReviewTranslator {
    
    @Override
    public Review toDomain(ReviewEntity entity) {
        return Review.builder()
                .id(entity.getId())
                .productId(entity.getProductId())
                .accountId(entity.getAccountId())
                .rating(new ReviewRate(entity.getRating()))
                .title(entity.getTitle())
                .comment(entity.getComment())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public ReviewEntity toEntity(Review domain) {
        return ReviewEntity.builder()
                .id(domain.getId())
                .productId(domain.getProductId())
                .accountId(domain.getAccountId())
                .rating(domain.getRating().value())
                .title(domain.getTitle())
                .comment(domain.getComment())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
