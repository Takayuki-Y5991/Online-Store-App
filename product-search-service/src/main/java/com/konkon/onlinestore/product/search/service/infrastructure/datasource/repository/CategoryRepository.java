package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository;

import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlClient;

public interface CategoryRepository {

    Uni<Category> searchCategory(int categoryId);

    Multi<Category> searchCategories();

    Uni<Boolean> createCategory(Category category, SqlClient client);
    
}
