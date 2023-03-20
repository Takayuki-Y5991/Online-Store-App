package com.konkon.onlinestore.product.search.service.domain.entity;

import com.konkon.onlinestore.product.search.service.domain.value.ProductId;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(ProductId id, String name, String description, BigDecimal price, String imageUrl, String category) {

    public static final class ProductBuilder {
        private ProductId id;
        private String name;
        private String description;
        private BigDecimal price;
        private String imageUrl;
        private String category;

        private ProductBuilder() {
        }

        public static ProductBuilder aProduct() {
            return new ProductBuilder();
        }

        public ProductBuilder withId(ProductId id) {
            this.id = id;
            return this;
        }

        public ProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductBuilder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ProductBuilder withCategory(String category) {
            this.category = category;
            return this;
        }

        public Product build() {
            return new Product(id, name, description, price, imageUrl, category);
        }
    }
}