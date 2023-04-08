package com.konkon.onlinestore.product.search.service.domain.entity;

import com.konkon.onlinestore.product.search.service.domain.value.ReviewRate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Review {
    private UUID id;
    private UUID productId;
    private String accountId;
    private ReviewRate rating;
    private String title;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Review build(
            UUID id,
            UUID productId,
            String accountId,
            ReviewRate rating,
            String title,
            String comment,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return Review.builder()
                .id(id)
                .productId(productId)
                .accountId(accountId)
                .rating(rating)
                .title(title)
                .comment(comment)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static Review createBuild(
            UUID productId,
            String accountId,
            ReviewRate rating,
            String title,
            String comment,
            LocalDateTime createdAt
    ) {
        return Review.builder()
                .id(UUID.randomUUID())
                .productId(productId)
                .accountId(accountId)
                .rating(rating)
                .title(title)
                .comment(comment)
                .createdAt(createdAt)
                .build();
    }

}
