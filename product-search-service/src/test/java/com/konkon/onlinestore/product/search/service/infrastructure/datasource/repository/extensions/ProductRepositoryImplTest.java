package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions;

import com.konkon.onlinestore.product.search.service.config.PostgresResource;
import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.value.Price;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.ProductRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
class ProductRepositoryImplTest {

    private final ProductRepository repository;
    private final PgPool client;

    @Inject
    ProductRepositoryImplTest(ProductRepository repository, PgPool client) {
        this.repository = repository;
        this.client = client;
    }

    @Test
    void Search_Product_Success() {
        UUID productId = UUID.fromString("d9e4c6de-4e3f-4a57-8aaf-06e54c6c45e1");
        Uni<Product> result = repository.searchProduct(productId);

        assertThat(result, notNullValue());
        assertThat(result.await().indefinitely().getId(), equalTo(productId));
    }

    @Test
    void Search_Products_Success() {
        Multi<Product> result = repository.searchProducts(null, "desc", 10, 0);

        assertThat(result, notNullValue());
        assertThat(result.collect().asList().await().indefinitely(), not(Collections.EMPTY_LIST));
    }

    @Test
    void Search_Products_With_Category_Id_Success() {
        Multi<Product> result = repository.searchProductsWithCategoryId(1, null, null, 10, 0);

        assertThat(result, notNullValue());
        assertThat(result.collect().asList().await().indefinitely(), not(Collections.EMPTY_LIST));
    }

    @Test
    void Create_Product_Success() {
        Category category = Category.createBuild(1);

        Product newProduct = Product.createBuild(
                "テスト本",
                "テスト本の説明",
                new Price(BigDecimal.valueOf(500)),
                "https://example.com/testbook.jpg",
                category
        );

        Uni<Product> result = repository.createProduct(newProduct, client);
        assertThat(result, notNullValue());
        assertThat(result.await().indefinitely().getId(), equalTo(newProduct.getId()));
    }

    @Test
    void Update_Product_Success() {
        UUID productId = UUID.fromString("d9e4c6de-4e3f-4a57-8aaf-06e54c6c45e1");
        Category category = Category.createBuild(1);
        Product updatedProduct = Product.build(
                productId,
                "更新された本",
                "更新された説明",
                new Price(BigDecimal.valueOf(1000)),
                "https://example.com/updatedbook.jpg",
                1,
                category);

        Uni<Product> result = repository.updateProduct(updatedProduct, client);
        assertThat(result, notNullValue());
        assertThat(result.await().indefinitely().getVersion(), is(updatedProduct.getVersion() + 1));
    }

    @Test
    void Delete_Product_Success() {
        UUID productId = UUID.fromString("0cd36a3d-6d00-4a24-8f54-3a3c2d13b8c9");
        Uni<Boolean> result = repository.deleteProduct(productId, client);

        assertThat(result, notNullValue());
        assertThat(result.await().indefinitely(), is(true));
    }
}