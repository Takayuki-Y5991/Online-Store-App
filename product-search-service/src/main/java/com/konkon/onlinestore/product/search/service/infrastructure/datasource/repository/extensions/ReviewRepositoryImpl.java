package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Review;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.ReviewEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.ReviewRepository;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions.command.ReviewCommand;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator.ReviewTranslator;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlClient;
import io.vertx.mutiny.sqlclient.Tuple;
import org.apache.commons.lang3.ObjectUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewTranslator reviewTranslator;

    private final PgPool client;

    @Inject
    public ReviewRepositoryImpl(ReviewTranslator reviewTranslator, PgPool client) {
        this.reviewTranslator = reviewTranslator;
        this.client = client;
    }

    @Override
    public Uni<Review> searchReview(UUID id) {
        return client
                .preparedQuery(ReviewCommand.FETCH_BY_ID)
                .execute(Tuple.of(id.toString()))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? toReview(iterator.next()) : null)
                .onItem().ifNotNull().transform(reviewTranslator::toDomain);
    }

    @Override
    public Multi<Review> searchReviews(UUID productId) {
        return this.executeFetchByProductId(productId, this.client);
    }

    private Multi<Review> executeFetchByProductId(UUID productId, SqlClient client) {
        return client
                .preparedQuery(ReviewCommand.FETCH_BY_PRODUCT_ID)
                .execute(Tuple.of(productId))
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(this::toReview)
                .filter(Objects::nonNull)
                .onItem().transform(entity -> ObjectUtils.isNotEmpty(entity) ? reviewTranslator.toDomain(entity) : null);
    }

    private ReviewEntity toReview(Row row) {
        return ReviewEntity.builder()
                .id(row.getUUID("id"))
                .productId(row.getUUID("product_id"))
                .accountId(row.getString("account_uid"))
                .rating(row.getInteger("rating"))
                .title(row.getString("title"))
                .comment(row.getString("comment"))
                .createdAt(row.getLocalDateTime("created_at"))
                .updatedAt(row.getLocalDateTime("updated_at"))
                .build();
    }


    @Override
    public Multi<Review> searchAccountReviews(String accountId) {
        return this.executeFetchByAccountId(accountId, this.client);
    }

    private Multi<Review> executeFetchByAccountId(String accountId, PgPool client) {

        return client
                .preparedQuery(ReviewCommand.FETCH_BY_ACCOUNT_ID)
                .execute(Tuple.of(accountId))
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(this::toReview)
                .filter(Objects::nonNull)
                .onItem().transform(reviewTranslator::toDomain);
    }

    @Override
    public Uni<Review> createReview(Review review, SqlClient client) {
        return client
                .preparedQuery(ReviewCommand.INSERT)
                .execute(insertTuple(review))
                .onItem().transform(row -> row.rowCount() > 0 ? review : null);
    }

    private Tuple insertTuple(Review review) {
        return Tuple.wrap(Arrays.asList(
                review.getId(),
                review.getProductId(),
                review.getAccountId(),
                review.getRating().value(),
                review.getTitle(),
                review.getComment(),
                review.getCreatedAt(),
                review.getUpdatedAt()));
    }

    @Override
    public Uni<Boolean> deleteReview(UUID reviewId, SqlClient client) {
        return client
                .preparedQuery(ReviewCommand.DELETE)
                .execute(Tuple.of(reviewId))
                .onItem().transform(row -> row.rowCount() == 1);
    }

    @Override
    public Uni<Boolean> updateReview(Review review, SqlClient client) {
        LocalDateTime newTimeStamp = LocalDateTime.now();

        return client
                .preparedQuery(ReviewCommand.UPDATE)
                .execute(updateTuple(review, newTimeStamp))
                .onItem().transform(rows -> rows.rowCount() == 1);
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
        return Review.build(
                review.getId(),
                review.getProductId(),
                review.getAccountId(),
                review.getRating(),
                review.getTitle(),
                review.getComment(),
                review.getCreatedAt(),
                timestamp
        );
    }


}
