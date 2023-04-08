package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions;

import com.konkon.onlinestore.product.search.service.config.PostgresResource;
import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
class CategoryRepositoryImplTest {

    private final CategoryRepositoryImpl repository;
    private final PgPool client;

    @Inject
    CategoryRepositoryImplTest(CategoryRepositoryImpl repository, PgPool client) {
        this.repository = repository;
        this.client = client;
    }

    @Test
    void Search_Category_Success() {
        Uni<Category> actual = repository.searchCategory(1);

        assertThat(actual, notNullValue());
        assertThat(actual.await().indefinitely().getId(), equalTo(1));
    }

    @Test
    void Search_Categories_Success() {
        Multi<Category> actual = repository.searchCategories();

        assertThat(actual, notNullValue());
        assertThat(actual.collect().asList().await().indefinitely(), hasSize(3));
    }

    @Test
    void Create_Category_Success() {
        Category category = Category.build(4, "テスト項目");
        Uni<Boolean> actual = repository.createCategory(category, client);
        assertThat(actual, notNullValue());
        assertThat(actual.await().indefinitely().booleanValue(), is(true));
    }
}