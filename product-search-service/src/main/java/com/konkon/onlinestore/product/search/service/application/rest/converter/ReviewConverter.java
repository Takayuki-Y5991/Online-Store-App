package com.konkon.onlinestore.product.search.service.application.rest.converter;

import com.konkon.onlinestore.product.search.service.application.rest.model.ReviewResponse;
import com.konkon.onlinestore.product.search.service.domain.entity.Review;

public interface ReviewConverter {

    ReviewResponse toResponse(Review domain);
}
