package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.CategoryEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.CategoryRepository;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions.command.CategoryCommand;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator.CategoryTranslator;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlClient;
import io.vertx.mutiny.sqlclient.Tuple;
import org.apache.commons.lang3.ObjectUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;

@ApplicationScoped
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryTranslator categoryTranslator;
    private final PgPool client;


    @Inject
    public CategoryRepositoryImpl(CategoryTranslator categoryTranslator, PgPool client) {
        this.categoryTranslator = categoryTranslator;
        this.client = client;
    }

    @Override
    public Uni<Category> searchCategory(int categoryId) {
        return this.executeFetchById(categoryId, this.client);
    }

    private Uni<Category> executeFetchById(int categoryId, SqlClient client) {
        return client
                .preparedQuery(CategoryCommand.FETCH_BY_ID)
                .execute(Tuple.of(categoryId))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? toCategory(iterator.next()) : null)
                .onItem().transform(entity -> ObjectUtils.isNotEmpty(entity) ? categoryTranslator.toDomain(entity) : null);
    }

    @Override
    public Multi<Category> searchCategories() {
        return client
                .preparedQuery(CategoryCommand.FETCH)
                .execute()
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(this::toCategory)
                .filter(Objects::nonNull)
                .onItem().transform(categoryTranslator::toDomain);
    }

    @Override
    public Uni<Boolean> createCategory(Category category, SqlClient client) {
        return client
                .preparedQuery(CategoryCommand.INSERT)
                .execute(Tuple.of(Integer.toString(category.getId())))
                .onItem().transform(rows -> rows.rowCount() == 1)
                .onFailure().invoke(
                        throwable -> new IllegalArgumentException(String.format("%s is existed from category", category.getName()))
                );
    }

    private CategoryEntity toCategory(Row row) {
        return CategoryEntity.builder()
                .id(row.getInteger("id"))
                .name(row.getString("name"))
                .build();
    }
}
