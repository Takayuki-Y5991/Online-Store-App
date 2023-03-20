package com.konkon.onlinestore.product.search.service.domain.value;

import java.util.UUID;

public record ProductId(UUID id) {
    public static final class ProductIdBuilder {
        private UUID id;

        private ProductIdBuilder() {
        }

        public static ProductIdBuilder aProductId() {
            return new ProductIdBuilder();
        }

        public ProductIdBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        public ProductId build() {
            return new ProductId(id);
        }
    }
}
