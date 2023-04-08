package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions;

import com.konkon.onlinestore.product.search.service.config.PostgresResource;
import com.konkon.onlinestore.product.search.service.domain.entity.Review;
import com.konkon.onlinestore.product.search.service.domain.value.ReviewRate;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.ReviewRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
class ReviewRepositoryImplTest {

    private final ReviewRepository repository;

    private final PgPool client;

    ReviewRepositoryImplTest(ReviewRepository repository, PgPool client) {
        this.repository = repository;
        this.client = client;
    }

    @Test
    void Search_Review_Success() {
        UUID reviewId = UUID.fromString("d6194e4b-74a0-4b7e-8cfd-3b0f8b47abaf");
        Uni<Review> actual = repository.searchReview(reviewId);

        assertThat(actual, notNullValue());
        assertThat(actual.await().indefinitely().getId(), equalTo(reviewId));
    }

    @Test
    void Search_Reviews_Success() {
        UUID productId = UUID.fromString("0cd36a3d-6d00-4a24-8f54-3a3c2d13b8c9");

        Multi<Review> actual = repository.searchReviews(productId);
        assertThat(actual, notNullValue());
    }

    @Test
    void Search_Account_Reviews_Success() {
        String accountId = "3d3e6f11-32e7-4dd2-8263-a3a8fde49f7b";
        Multi<Review> actual = repository.searchAccountReviews(accountId);

        assertThat(actual, notNullValue());
    }

    @Test
    void Create_Review_Success() {
        UUID productId = UUID.fromString("6d302d6b-8c63-42f9-a9f0-56aebc32b8f6");
        String accountId = "3d3e6f11-32e7-4dd2-8263-a3a8fde49f7b";
        Review review = Review.createBuild(
                productId,
                accountId,
                new ReviewRate(3),
                "最高の食品",
                "良い商品でした。",
                LocalDateTime.now()
        );

        Uni<Review> actual = repository.createReview(review, client);
        assertThat(actual, notNullValue());
        assertThat(actual.await().indefinitely().getId(), notNullValue());
    }

    @Test
    void Update_Review_Success() {
        UUID id = UUID.fromString("d6194e4b-74a0-4b7e-8cfd-3b0f8b47abaf");
        UUID productId = UUID.fromString("d9e4c6de-4e3f-4a57-8aaf-06e54c6c45e1");
        String accountId = "3d3e6f11-32e7-4dd2-8263-a3a8fde49f7b";

        Review updatedReview = Review.build(
                id,
                productId,
                accountId,
                new ReviewRate(1),
                "テスト用更新",
                "テスト更新",
                LocalDateTime.parse("2023-01-15T15:30:00"),
                LocalDateTime.parse("2023-01-15T15:30:00")
        );
        Uni<Boolean> actual = repository.updateReview(updatedReview, client);
        assertThat(actual, notNullValue());
        assertThat(actual.await().indefinitely(), is(Boolean.TRUE));
    }

    @Test
    void Delete_Review_Success() {
        UUID productId = UUID.fromString("6d302d6b-8c63-42f9-a9f0-56aebc32b8f6");
        final String accountId = "3d3e6f11-32e7-4dd2-8263-a3a8fde49f7b";
        Review review = Review.createBuild(
                productId,
                accountId,
                new ReviewRate(1),
                "最高の食品",
                "良い商品でした。",
                LocalDateTime.now()
        );
        UUID newId = repository.createReview(review, client).await().indefinitely().getId();
        Uni<Boolean> actual = repository.deleteReview(newId, client);

        assertThat(actual, IsNull.notNullValue());
        assertThat(actual.await().indefinitely(), is(true));
    }
}