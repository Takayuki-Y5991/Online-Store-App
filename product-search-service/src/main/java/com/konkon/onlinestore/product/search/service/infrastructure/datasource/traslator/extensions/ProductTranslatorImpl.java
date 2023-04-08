package com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.value.Price;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.ProductEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator.CategoryTranslator;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator.ProductTranslator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ProductTranslatorImpl implements ProductTranslator {

    private final CategoryTranslator categoryTranslator;

    @Inject
    public ProductTranslatorImpl(CategoryTranslator categoryTranslator) {
        this.categoryTranslator = categoryTranslator;
    }

    @Override
    public Product toDomain(ProductEntity entity) {
        return Product.build(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                new Price(entity.getPrice()),
                entity.getImageUrl(),
                entity.getVersion(),
                categoryTranslator.toDomain(entity.getCategory())
        );
    }

    @Override
    public ProductEntity toEntity(Product domain) {
        return ProductEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .price(domain.getPrice().value())
                .description(domain.getDescription())
                .imageUrl(domain.getImageUrl())
                .version(domain.getVersion())
                .category(categoryTranslator.toEntity(domain.getCategory()))
                .build();
    }
}
