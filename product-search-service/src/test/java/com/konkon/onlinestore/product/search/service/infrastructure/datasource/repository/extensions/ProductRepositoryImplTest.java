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
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
        assertThat(result.collect().asList().await().indefinitely(), hasSize(3));
    }

    @Test
    void Search_Products_With_Category_Id_Success() {
        Multi<Product> result = repository.searchProductsWithCategoryId(1, null, null, 10, 0);

        assertThat(result, notNullValue());
        assertThat(result.collect().asList().await().indefinitely(), hasSize(1));
    }

    @Test
    void Create_Product_Success() {
        Category category = Category.builder()
                .id(1)
                .name("本")
                .build();

        Product newProduct = Product.builder()
                .id(UUID.randomUUID())
                .name("テスト本")
                .price(new Price(BigDecimal.valueOf(500)))
                .description("テスト本の説明")
                .imageUrl("https://example.com/testbook.jpg")
                .category(category)
                .build();

        Uni<Product> result = repository.createProduct(newProduct, client);
        assertThat(result, notNullValue());
        assertThat(result.await().indefinitely().getId(), equalTo(newProduct.getId()));
    }

    @Test
    void Update_Product_Success() {
        UUID productId = UUID.fromString("d9e4c6de-4e3f-4a57-8aaf-06e54c6c45e1");
        Category category = Category.builder()
                .id(1)
                .name("本")
                .build();

        Product updatedProduct = Product.builder()
                .id(productId)
                .name("更新された本")
                .price(new Price(BigDecimal.valueOf(1000)))
                .description("更新された説明")
                .imageUrl("https://example.com/updatedbook.jpg")
                .category(category)
                .version(1)
                .build();

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