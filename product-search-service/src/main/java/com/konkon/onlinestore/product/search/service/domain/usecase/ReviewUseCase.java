package com.konkon.onlinestore.product.search.service.domain.usecase;

import com.konkon.onlinestore.product.search.service.domain.entity.Review;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

public interface ReviewUseCase {
    Uni<Boolean> deleteProduct(UUID reviewId);

    Uni<Review> createProduct(Review review);

    Uni<Boolean> updateProduct(Review review);
}
