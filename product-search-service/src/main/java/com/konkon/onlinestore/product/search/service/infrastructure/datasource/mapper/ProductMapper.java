package com.konkon.onlinestore.product.search.service.infrastructure.datasource.mapper;

import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.value.Price;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.CategoryEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.ProductEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.Set;

@Mapper(componentModel = "cdi")
public interface ProductMapper {


    // Product
    @Mapping(source = "price", target = "price", qualifiedByName = "toPrice")
    @Mapping(source = "categories", target = "categories")
    Product toDomain(ProductEntity entity);

    @Mapping(source = "price", target = "price", qualifiedByName = "fromPrice")
    @Mapping(source = "categories", target = "categories")
    ProductEntity toEntity(Product domain);

    @Named("toPrice")
    default Price toPrice(BigDecimal price) {
        return new Price(price);
    }

    @Named("fromPrice")
    default BigDecimal fromPrice(Price price) {
        return price.value();
    }

    // Categories conversion
    @IterableMapping(qualifiedByName = "toDomainCategory")
    Set<Category> toDomain(Set<CategoryEntity> entities);

    @IterableMapping(qualifiedByName = "toEntityCategory")
    Set<CategoryEntity> toEntity(Set<Category> domains);

    @Named("toDomainCategory")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    Category toDomain(CategoryEntity entity);

    @Named("toEntityCategory")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    CategoryEntity toEntity(Category domain);
}
