package com.konkon.onlinestore.product.search.service.application.rest.converter;

import com.konkon.onlinestore.product.search.service.application.rest.model.response.CategoryResponse;
import com.konkon.onlinestore.product.search.service.domain.entity.Category;

public interface CategoryConverter {

    CategoryResponse toResponse(Category domain);
}
