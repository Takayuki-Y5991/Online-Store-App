package com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.config.PgPoolTransaction;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.Map;
import java.util.UUID;

public interface ProductRepository {

    Uni<Product> searchProduct(UUID productId);

    Uni<Product> searchProduct(UUID productId, PgPoolTransaction tx);

    Multi<Product> searchProducts(Map<String, String> query);

    Uni<Product> createProduct(Product product);

    Uni<Boolean> deleteProduct(UUID uuid);

    Uni<Product> updateProduct(Product product);
}
