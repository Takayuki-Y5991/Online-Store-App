package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Review;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.ReviewEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.ReviewRepository;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator.ReviewTranslator;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlClient;
import io.vertx.mutiny.sqlclient.Tuple;
import org.apache.commons.lang3.ObjectUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@ApplicationScoped
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewTranslator reviewTranslator;

    private final PgPool client;

    private final String FETCH_BY_PRODUCT_ID = "SELECT * FROM reviews WHERE product_id = $1";
    private final String FETCH_BY_ACCOUNT_ID = "SELECT * FROm reviews WHERE account_id = $1";
    private final String INSERT = "INSERT INTO reviews VALUES ($1, $2, $3, $4, $5, $6, $7, $8)";
    private final String DELETE = "DELETE FROM reviews WHERE id = $1";
    private final String UPDATE = "UPDATE reviews SET rating = $1, title = $2, comment = $3, updated_at = $4 WHERE id = $5 AND updated_at = $6";

    @Inject
    public ReviewRepositoryImpl(ReviewTranslator reviewTranslator, PgPool client) {
        this.reviewTranslator = reviewTranslator;
        this.client = client;
    }

    @Override
    public Multi<Review> searchReviews(UUID productId) {
        return this.executeFetchByProductId(productId, this.client);
    }

    private Multi<Review> executeFetchByProductId(UUID productId, SqlClient client) {
        return client
                .preparedQuery(FETCH_BY_PRODUCT_ID)
                .execute(Tuple.of(productId))
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(row -> toReview(row))
                .onItem().transform(entity -> ObjectUtils.isNotEmpty(entity) ? reviewTranslator.toDomain(entity) : null);
    }

    private ReviewEntity toReview(Row row) {
        return ReviewEntity.builder()
                .id(row.getUUID("id"))
                .productId(row.getUUID("productId"))
                .accountId(row.getUUID("accountId"))
                .rating(row.getInteger("rating"))
                .title(row.getString("title"))
                .comment(row.getString("comment"))
                .createdAt(row.getLocalDateTime("createdAt"))
                .updatedAt(row.getLocalDateTime("updatedAt"))
                .build();
    }


    @Override
    public Multi<Review> searchAccountReviews(UUID accountId) {
        return this.executeFetchByAccountId(accountId, this.client);
    }

    private Multi<Review> executeFetchByAccountId(UUID accountId, PgPool client) {
        return client
                .preparedQuery(FETCH_BY_ACCOUNT_ID)
                .execute(Tuple.of(accountId))
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(row -> toReview(row))
                .onItem().transform(entity -> ObjectUtils.isNotEmpty(entity) ? reviewTranslator.toDomain(entity) : null);
    }

    @Override
    public Uni<Review> createReview(Review review, SqlClient client) {
        return client
                .preparedQuery(INSERT)
                .execute(insertTuple(review))
                .onItem().transform(row -> row.rowCount() > 0 ? review : null);
    }

    private Tuple insertTuple(Review review) {
        return Tuple.wrap(Arrays.asList(
                review.getId(),
                review.getProductId(),
                review.getAccountId(),
                review.getRating(),
                review.getTitle(),
                review.getComment(),
                review.getCreatedAt(),
                review.getUpdatedAt()));
    }

    @Override
    public Uni<Boolean> deleteReview(UUID reviewId, SqlClient client) {
        return client
                .preparedQuery(DELETE)
                .execute(Tuple.of(reviewId))
                .onItem().transform(row -> row.rowCount() == 1);
    }

    @Override
    public Uni<Review> updateReview(Review review, SqlClient client) {
        LocalDateTime newTimeStamp = LocalDateTime.now();
        return client
                .preparedQuery(UPDATE)
                .execute(updateTuple(review, newTimeStamp))
                .onItem().transform(rows -> rows.rowCount() > 0 ? updatedReview(review, newTimeStamp) : null);
    }

    private Tuple updateTuple(Review review, LocalDateTime newTimeStamp) {
        return Tuple.wrap(Arrays.asList(
                review.getRating().value(),
                review.getTitle(),
                review.getComment(),
                newTimeStamp,
                review.getId(),
                review.getUpdatedAt(),
                review.getAccountId(),
                review.getProductId()));
    }

    private Review updatedReview(Review review, LocalDateTime timestamp) {
        return Review.builder()
                .id(review.getId())
                .productId(review.getProductId())
                .accountId(review.getAccountId())
                .rating(review.getRating())
                .title(review.getTitle())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .updatedAt(timestamp)
                .build();
    }


}
