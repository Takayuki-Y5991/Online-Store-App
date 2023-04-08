package com.konkon.onlinestore.product.search.service.domain.usecase.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import com.konkon.onlinestore.product.search.service.domain.usecase.CategoryUseCase;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.CategoryRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CategoryUseCaseImpl implements CategoryUseCase {

    private final CategoryRepository repository;
    private final PgPool client;

    @Inject
    public CategoryUseCaseImpl(CategoryRepository repository, PgPool client) {
        this.repository = repository;
        this.client = client;
    }

    @Override
    public Uni<Boolean> createCategory(Category category) {
        return client.withTransaction(conn ->
                repository.createCategory(category, conn));
    }

    @Override
    public Multi<Category> searchCategories() {
        return repository.searchCategories();
    }
}
