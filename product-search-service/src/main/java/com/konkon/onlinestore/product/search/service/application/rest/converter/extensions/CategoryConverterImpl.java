package com.konkon.onlinestore.product.search.service.application.rest.converter.extensions;

import com.konkon.onlinestore.product.search.service.application.rest.converter.CategoryConverter;
import com.konkon.onlinestore.product.search.service.application.rest.model.response.CategoryResponse;
import com.konkon.onlinestore.product.search.service.domain.entity.Category;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryConverterImpl implements CategoryConverter {
    @Override
    public CategoryResponse toResponse(Category domain) {
        return new CategoryResponse(
                domain.getId(),
                domain.getName()
        );
    }
}
