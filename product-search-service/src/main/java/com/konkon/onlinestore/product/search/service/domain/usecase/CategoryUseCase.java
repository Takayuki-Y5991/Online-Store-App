package com.konkon.onlinestore.product.search.service.domain.usecase;

import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface CategoryUseCase {

    Uni<Boolean> createCategory(Category category);

    Multi<Category> searchCategories();
}
