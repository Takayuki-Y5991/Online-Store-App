package com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.ProductEntity;

public interface ProductTranslator {

    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product domain);
}
