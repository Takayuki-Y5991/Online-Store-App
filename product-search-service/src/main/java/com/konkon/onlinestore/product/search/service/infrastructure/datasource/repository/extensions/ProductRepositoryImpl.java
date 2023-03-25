package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.config.DatabaseClientProvider;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.CategoryEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.ProductEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.mapper.ProductMapper;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.ProductRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductMapper productMapper;
    private final DatabaseClientProvider client;

    private final String FETCH_BY_ID = "SELECT p.*, c.* FROM products p INNER JOIN categories c ON p.category_id = c.id WHERE p.id = $1";

    @Inject
    public ProductRepositoryImpl(ProductMapper productMapper, DatabaseClientProvider client) {
        this.productMapper = productMapper;
        this.client = client;
    }


    @Override
    public Uni<Product> searchProduct(UUID productId) {
        var result = client.getClient()
                .preparedQuery(FETCH_BY_ID)
                .execute(Tuple.of(productId))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? toProduct(iterator.next()) : null);
        return null;
    }

    @Override
    public Multi<Product> searchProducts(Map<String, String> query) {
        return null;
    }

    @Override
    public Uni<Product> createProduct(Product product) {
        return null;
    }

    @Override
    public Uni<Boolean> deleteProduct(UUID uuid) {
        return null;
    }

    @Override
    public Uni<Product> updateProduct(Product product) {
        return null;
    }

    private ProductEntity toProduct(Row row) {
        CategoryEntity category = CategoryEntity.builder()
                .id(row.getInteger("c.id"))
                .name(row.getString("c.name"))
                .build();
        ProductEntity entity = ProductEntity.builder()
                .id(row.getUUID("p.id"))
                .name(row.getString("p.name"))
                .price(row.getBigDecimal("p.price"))
                .description(row.getString("p.description"))
                .imageUrl(row.getString("p.imageUrl"))
                .category(category)
                .build();
        return entity;
    }
}
