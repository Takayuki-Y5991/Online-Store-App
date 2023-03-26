package com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator;

import com.konkon.onlinestore.product.search.service.domain.entity.Review;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.ReviewEntity;

public interface ReviewTranslator {
    Review toDomain(ReviewEntity entity);

    ReviewEntity toEntity(Review domain);
}
