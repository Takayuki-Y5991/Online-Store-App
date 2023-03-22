package com.konkon.onlinestore.product.search.service.application.rest.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String name,
        BigDecimal price,
        String description,
        String imageUrl,
        List<CategoryResponse> categories
) {
}
