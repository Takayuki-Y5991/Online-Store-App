package com.konkon.onlinestore.product.search.service.domain.entity;

import com.konkon.onlinestore.product.search.service.domain.value.ReviewRate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class Review {
    private UUID id;
    private UUID productId;
    private UUID accountId;
    private ReviewRate rating;
    private String title;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
