package com.konkon.onlinestore.product.search.service.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "category_name", nullable = false)
    private String name;

    public Long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public static final class CategoryEntityBuilder {
        private Long categoryId;
        private String name;

        private CategoryEntityBuilder() {
        }

        public static CategoryEntityBuilder aCategoryEntity() {
            return new CategoryEntityBuilder();
        }

        public CategoryEntityBuilder withCategoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public CategoryEntityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CategoryEntity build() {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.name = this.name;
            categoryEntity.categoryId = this.categoryId;
            return categoryEntity;
        }
    }
}
