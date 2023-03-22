package com.konkon.onlinestore.product.search.service.application.rest.converter;

import com.konkon.onlinestore.product.search.service.application.rest.model.ProductResponse;
import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ProductConverter {
    ProductResponse toResponse(Product product);
}
