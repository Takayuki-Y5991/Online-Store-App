package com.konkon.onlinestore.product.search.service.domain.value;

import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID productId) {
    public ProductId(UUID productId) {
        this.productId = (Objects.isNull(productId)) ? UUID.randomUUID() : productId;
    }
}
