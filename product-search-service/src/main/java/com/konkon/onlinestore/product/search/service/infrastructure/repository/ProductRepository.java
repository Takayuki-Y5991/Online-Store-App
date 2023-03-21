package com.konkon.onlinestore.product.search.service.infrastructure.repository;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.value.ProductId;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductRepository {

    Uni<Product> searchProduct(ProductId productId);
    Multi<Product> searchProducts(Map<String, String> query);
    Uni<Product> createProduct(Product product);
    Uni<Boolean> deleteProduct(UUID uuid);
    Uni<Product> updateProduct(Product product);
}
