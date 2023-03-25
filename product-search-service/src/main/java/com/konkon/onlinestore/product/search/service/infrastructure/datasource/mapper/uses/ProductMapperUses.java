package com.konkon.onlinestore.product.search.service.infrastructure.datasource.mapper.uses;

import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import com.konkon.onlinestore.product.search.service.domain.value.Price;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.CategoryEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.mapper.CategoryMapper;
import org.mapstruct.Named;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;

@Named("ProductMapperUses")
@ApplicationScoped
public class ProductMapperUses {

    private final CategoryMapper categoryMapper;

    @Inject
    public ProductMapperUses(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Named("toPrice")
    public Price toPrice(BigDecimal price) {
        return new Price(price);
    }

    @Named("fromPrice")
    public BigDecimal fromPrice(Price price) {
        return price.value();
    }

    @Named("toCategory")
    public Category toCategory(CategoryEntity entity) {
        return categoryMapper.toDomain(entity);
    }

    @Named("fromCategory")
    public CategoryEntity fromCategory(Category domain) {
        return categoryMapper.toEntity(domain);
    }
}
