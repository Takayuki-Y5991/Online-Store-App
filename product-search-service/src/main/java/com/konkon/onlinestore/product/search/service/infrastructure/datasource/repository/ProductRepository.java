package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlClient;

import java.util.UUID;

public interface ProductRepository {

    Uni<Product> searchProduct(UUID productId);

    Uni<Product> searchProduct(UUID productId, SqlClient client);

    Multi<Product> searchProducts(String sortKey, String order, Integer limit, Integer offset);

    Uni<Product> createProduct(Product product, SqlClient client);

    Uni<Boolean> deleteProduct(UUID productId, SqlClient client);

    Uni<Product> updateProduct(Product product, SqlClient client);
}
