package com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.CategoryEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator.CategoryTranslator;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryTranslatorImpl implements CategoryTranslator {
    @Override
    public Category toDomain(CategoryEntity entity) {
        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public CategoryEntity toEntity(Category domain) {
        return CategoryEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }
}
