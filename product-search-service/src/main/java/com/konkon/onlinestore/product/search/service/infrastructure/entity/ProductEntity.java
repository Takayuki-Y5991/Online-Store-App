package com.konkon.onlinestore.product.search.service.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @Column(name = "product_id", nullable = false)
    private UUID productId;
    @Column(name = "product_name", nullable = false, length = 100)
    private String name;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;
    @ManyToMany
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<CategoryEntity> categoryEntities;

    public UUID getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static final class ProductEntityBuilder {
        private UUID productId;
        private String name;
        private BigDecimal price;
        private String description;
        private String imageUrl;

        private ProductEntityBuilder() {
        }

        public static ProductEntityBuilder aProductEntity() {
            return new ProductEntityBuilder();
        }

        public ProductEntityBuilder withProductId(UUID productId) {
            this.productId = productId;
            return this;
        }

        public ProductEntityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ProductEntityBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductEntityBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductEntityBuilder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ProductEntity build() {
            ProductEntity productEntity = new ProductEntity();
            productEntity.price = this.price;
            productEntity.description = this.description;
            productEntity.productId = this.productId;
            productEntity.imageUrl = this.imageUrl;
            productEntity.name = this.name;
            return productEntity;
        }
    }
}
