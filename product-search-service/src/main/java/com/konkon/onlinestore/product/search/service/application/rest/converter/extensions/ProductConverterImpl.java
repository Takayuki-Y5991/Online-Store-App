package com.konkon.onlinestore.product.search.service.application.rest.converter.extensions;

import com.konkon.onlinestore.product.search.service.application.rest.converter.CategoryConverter;
import com.konkon.onlinestore.product.search.service.application.rest.converter.ProductConverter;
import com.konkon.onlinestore.product.search.service.application.rest.model.ProductResponse;
import com.konkon.onlinestore.product.search.service.domain.entity.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ProductConverterImpl implements ProductConverter {

    private final CategoryConverter categoryConverter;

    @Inject
    public ProductConverterImpl(CategoryConverter categoryConverter) {
        this.categoryConverter = categoryConverter;
    }

    @Override
    public ProductResponse toResponse(Product domain) {
        return new ProductResponse(
                domain.getId(),
                domain.getName(),
                domain.getPrice().value(),
                domain.getDescription(),
                domain.getImageUrl(),
                categoryConverter.toResponse(domain.getCategory()));
    }
}
