package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.CategoryEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.ProductEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.ProductRepository;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator.ProductTranslator;
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
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductTranslator productTranslator;
    private final PgPool client;

    private final int INIT_VERSION = 1;

    private final String FECTH = "SELECT p.*, c.name as category_name FROM products p INNER JOIN categories c ON p.category_id = c.id ORDER BY $1 %s LIMIT $2 OFFSET $3";
    private final String FETCH_BY_ID = "SELECT p.*, c.name as category_name FROM products p INNER JOIN categories c ON p.category_id = c.id WHERE p.id = $1";
    private final String INSERT = "INSERT INTO products VALUES ($1, $2, $3, $4, $5, $6, $7)";
    private final String DELETE = "DELETE FROM products WHERE id = $1";
    private final String UPDATE = "UPDATE products SET name = $1, price = $2, description = $3, image_url = $4, category_id = $5, version = version + 1 WHERE id = $6 AND version = $7";

    @Inject
    public ProductRepositoryImpl(ProductTranslator productTranslator, PgPool client) {
        this.productTranslator = productTranslator;
        this.client = client;
    }


    @Override
    public Uni<Product> searchProduct(UUID productId) {
        return this.executeFetchById(productId, this.client);
    }

    @Override
    public Uni<Product> searchProduct(UUID productId, SqlClient txClient) {
        return this.executeFetchById(productId, txClient);
    }

    private Uni<Product> executeFetchById(UUID productId, SqlClient client) {
        return client
                .preparedQuery(FETCH_BY_ID)
                .execute(Tuple.of(productId))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? toProduct(iterator.next()) : null)
                .onItem().transform(entity -> ObjectUtils.isNotEmpty(entity) ? productTranslator.toDomain(entity) : null);
    }

    @Override
    public Multi<Product> searchProducts(String sortKey, String order, Integer limit, Integer offset) {

        final String query = setOrderInQuery(FECTH, order);

        return client
                .preparedQuery(query)
                .execute(selectTuple(sortKey, limit, offset))
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(this::toProduct)
                .onItem().transform(entity -> ObjectUtils.isNotEmpty(entity) ? productTranslator.toDomain(entity) : null);
    }

    private Tuple selectTuple(String key, Integer limit, Integer offset) {
        return Tuple.wrap(Arrays.asList(
                Objects.isNull(key) ? " p.id" : key,
                Objects.isNull(limit) ? 10 : limit,
                Objects.isNull(offset) ? 0 : offset));
    }

    /**
     * PgPoolライブラリのプレースフォルダにORDERの関連句挿入ができなかったため、Orderの順序に関して定義する関数
     *
     * @param baseQuery 「%s」を含むQuery
     * @param order     順序 [ASC or DESC]
     * @return Query
     */
    private String setOrderInQuery(String baseQuery, String order) {
        String finalOrder = Objects.isNull(order) ? "ASC" : order;
        return String.format(baseQuery, finalOrder);
    }

    @Override
    public Uni<Product> createProduct(Product product, SqlClient client) {
        return client
                .preparedQuery(INSERT)
                .execute(insertTuple(product))
                .onItem().transform(rows -> rows.rowCount() > 0 ? product : null);
    }

    private Tuple insertTuple(Product product) {
        return Tuple.wrap(Arrays.asList(
                product.getId(),
                product.getName(),
                product.getPrice().value(),
                product.getDescription(),
                product.getImageUrl(),
                product.getCategory().getId(),
                INIT_VERSION
        ));
    }

    @Override
    public Uni<Boolean> deleteProduct(UUID productId, SqlClient client) {
        return client
                .preparedQuery(DELETE)
                .execute(Tuple.of(productId))
                .onItem().transform(rows -> rows.rowCount() == 1);
    }

    @Override
    public Uni<Product> updateProduct(Product product, SqlClient client) {
        return client
                .preparedQuery(UPDATE)
                .execute(updateTuple(product))
                .onItem().transform(rows -> rows.rowCount() > 0 ? updateProduct(product) : null);
    }

    private Tuple updateTuple(Product product) {
        return Tuple.wrap(Arrays.asList(
                product.getName(),
                product.getPrice().value(),
                product.getDescription(),
                product.getImageUrl(),
                product.getCategory().getId(),
                product.getId(),
                product.getVersion()));
    }

    private Product updateProduct(Product product) {
        return Product.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .category(Category.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .build())
                .version(product.getVersion() + 1)
                .build();
    }


    private ProductEntity toProduct(Row row) {
        CategoryEntity category = CategoryEntity.builder()
                .id(row.getInteger("category_id"))
                .name(row.getString("category_name"))
                .build();
        return ProductEntity.builder()
                .id(row.getUUID("id"))
                .name(row.getString("name"))
                .price(row.getBigDecimal("price"))
                .description(row.getString("description"))
                .imageUrl(row.getString("image_url"))
                .version(row.getInteger("version"))
                .category(category)
                .build();
    }
}
