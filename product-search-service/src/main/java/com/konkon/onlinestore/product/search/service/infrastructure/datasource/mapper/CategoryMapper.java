package com.konkon.onlinestore.product.search.service.infrastructure.datasource.mapper;

import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.CategoryEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface CategoryMapper {
    Category toDomain(CategoryEntity entity);

    CategoryEntity toEntity(Category domain);
}
