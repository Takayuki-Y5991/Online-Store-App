package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.CategoryEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.entity.ProductEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.ProductRepository;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions.command.ProductCommand;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions.helper.InsertHelper;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.extensions.helper.QueryHelper;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.traslator.ProductTranslator;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlClient;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductTranslator productTranslator;
    private final PgPool client;

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
                .preparedQuery(ProductCommand.FETCH_BY_ID)
                .execute(Tuple.of(productId))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? toProduct(iterator.next()) : null)
                .onItem().ifNotNull().transform(productTranslator::toDomain);
    }

    @Override
    public Multi<Product> searchProducts(String sortKey, String order, Integer limit, Integer offset) {

        final String query = QueryHelper.setOrderInQuery(ProductCommand.FECTH, order);

        return client
                .preparedQuery(query)
                .execute(selectTuple(sortKey, limit, offset))
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(this::toProduct)
                .filter(Objects::nonNull)
                .onItem().transform(productTranslator::toDomain);
    }

    private Tuple selectTuple(String key, Integer limit, Integer offset) {
        return Tuple.wrap(Arrays.asList(
                Objects.isNull(key) ? " p.id" : key,
                Objects.isNull(limit) ? 10 : limit,
                Objects.isNull(offset) ? 0 : offset));
    }

    @Override
    public Multi<Product> searchProductsWithCategoryId(Integer categoryId, String sortKey, String order, Integer limit, Integer offset) {

        final String query = QueryHelper.setOrderInQuery(ProductCommand.FECTH_BY_CATEGORY_ID, order);

        return client
                .preparedQuery(query)
                .execute(selectWithCategoryIdTuple(categoryId, sortKey, limit, offset))
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(this::toProduct)
                .filter(Objects::nonNull)
                .onItem().transform(productTranslator::toDomain);
    }

    private Tuple selectWithCategoryIdTuple(Integer categoryId, String key, Integer limit, Integer offset) {
        return Tuple.wrap(Arrays.asList(
                categoryId,
                Objects.isNull(key) ? " p.id" : key,
                Objects.isNull(limit) ? 10 : limit,
                Objects.isNull(offset) ? 0 : offset));
    }

    @Override
    public Uni<Product> createProduct(Product product, SqlClient client) {
        return client
                .preparedQuery(ProductCommand.INSERT)
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
                InsertHelper.INIT_VERSION
        ));
    }

    @Override
    public Uni<Boolean> deleteProduct(UUID productId, SqlClient client) {
        return client
                .preparedQuery(ProductCommand.DELETE)
                .execute(Tuple.of(productId))
                .onItem().transform(rows -> rows.rowCount() == 1);
    }

    @Override
    public Uni<Product> updateProduct(Product product, SqlClient client) {
        return client
                .preparedQuery(ProductCommand.UPDATE)
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
