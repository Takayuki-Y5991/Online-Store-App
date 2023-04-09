package com.konkon.onlinestore.product.search.service.application.rest.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record CreateReviewRequest(
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
        String comment
) {
}
