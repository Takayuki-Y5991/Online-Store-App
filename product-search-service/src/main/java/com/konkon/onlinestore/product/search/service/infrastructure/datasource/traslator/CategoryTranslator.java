package com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator;

import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.CategoryEntity;

public interface CategoryTranslator {
    Category toDomain(CategoryEntity entity);

    CategoryEntity toEntity(Category domain);
}
