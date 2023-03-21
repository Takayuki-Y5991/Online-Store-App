package com.konkon.onlinestore.product.search.service.domain.value;

import java.util.Objects;
import java.util.UUID;

public record Category(
        UUID categoryId,
        String categoryName
) {
    public Category(UUID categoryId, String categoryName) {
        this.categoryId = Objects.requireNonNull(categoryId, "CategoryId must be null");
        this.categoryName = Objects.requireNonNull(categoryName, "CategoryName must be null");
    }
}
