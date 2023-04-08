package com.konkon.onlinestore.product.search.service.domain.usecase;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.entity.aggregation.ProductReview;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

public interface ProductUseCase {
    Uni<ProductReview> searchProduct(UUID productId);

    Multi<Product> searchProducts(String key, String order, Integer limit, Integer offset);

    Uni<Product> createProduct(Product product);

    Uni<Product> updateProduct(Product product);

    Uni<Boolean> deleteProduct(UUID productId);

    Multi<Product> searchProductsWithCategoryId(Integer categoryId, String key, String order, Integer limit, Integer offset);
}
