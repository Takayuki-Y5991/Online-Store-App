package com.konkon.onlinestore.product.search.service.infrastructure.datasource.mapper;

import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CategoryMapper {
    Category toDomain(CategoryEntity entity);

    CategoryEntity toEntity(Category domain);
}
