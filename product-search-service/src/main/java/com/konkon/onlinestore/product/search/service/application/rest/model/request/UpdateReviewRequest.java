package com.konkon.onlinestore.product.search.service.application.rest.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateReviewRequest(
        @NotNull
        UUID productId,
        @NotNull
        String accountId,
        @NotNull
        @Min(1)
        @Max(5)
        Integer rating,
        @NotNull
        String title,
        @NotNull
        String comment,
        @Past
        @NotNull
        LocalDateTime createdAt,
        @NotNull
        LocalDateTime updatedAt
) {
}
