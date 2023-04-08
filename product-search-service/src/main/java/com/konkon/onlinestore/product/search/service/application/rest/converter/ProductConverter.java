package com.konkon.onlinestore.product.search.service.application.rest.converter;

import com.konkon.onlinestore.product.search.service.application.rest.model.CreateProductRequest;
import com.konkon.onlinestore.product.search.service.application.rest.model.ProductResponse;
import com.konkon.onlinestore.product.search.service.application.rest.model.UpdateProductRequest;
import com.konkon.onlinestore.product.search.service.domain.entity.Product;

import java.util.UUID;

public interface ProductConverter {
    ProductResponse toResponse(Product domain);

    Product toDomain(CreateProductRequest request);

    Product toDomain(UUID productId, UpdateProductRequest request);
}
