package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository;

import com.konkon.onlinestore.product.search.service.domain.entity.Review;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlClient;

import java.util.UUID;

public interface ReviewRepository {
    Multi<Review> searchReviews(UUID productId);

    Multi<Review> searchAccountReviews(UUID accountId);

    Uni<Review> createReview(Review review, SqlClient client);

    Uni<Boolean> deleteReview(UUID reviewId, SqlClient client);

    Uni<Review> updateReview(Review review, SqlClient client);
}
