package com.konkon.onlinestore.product.search.service.application.rest.model;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String name,
        BigDecimal price,
        String description,
        String imageUrl,
        int version,
        Integer categoryId
) {
}
