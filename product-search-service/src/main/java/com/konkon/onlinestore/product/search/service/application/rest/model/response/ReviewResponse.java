package com.konkon.onlinestore.product.search.service.application.rest.model.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewResponse(
        UUID id,
        UUID productId,
        String accountId,
        Integer rating,
        String title,
        String comment,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
