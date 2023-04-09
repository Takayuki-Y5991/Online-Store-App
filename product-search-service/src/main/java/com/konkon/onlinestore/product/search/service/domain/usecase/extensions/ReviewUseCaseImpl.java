package com.konkon.onlinestore.product.search.service.domain.usecase.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Review;
import com.konkon.onlinestore.product.search.service.domain.usecase.ReviewUseCase;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.ReviewRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class ReviewUseCaseImpl implements ReviewUseCase {

    private final ReviewRepository reviewRepository;
    private final PgPool client;

    @Inject
    public ReviewUseCaseImpl(ReviewRepository reviewRepository, PgPool clinet) {
        this.reviewRepository = reviewRepository;
        this.client = clinet;
    }

    @Override
    public Uni<Boolean> deleteProduct(UUID reviewId) {
        return client.withTransaction(conn ->
                reviewRepository.deleteReview(reviewId, conn));
    }

    @Override
    public Uni<Review> createProduct(Review review) {
        return client.withTransaction(conn ->
                reviewRepository.createReview(review, conn));
    }

    @Override
    public Uni<Boolean> updateProduct(Review review) {
        return client.withTransaction(conn ->
                reviewRepository.updateReview(review, conn));
    }
}
