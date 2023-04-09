package com.konkon.onlinestore.product.search.service.application.rest.model.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String name,
        BigDecimal price,
        String description,
        String imageUrl,
        Integer version,
        CategoryResponse category
) {
}
