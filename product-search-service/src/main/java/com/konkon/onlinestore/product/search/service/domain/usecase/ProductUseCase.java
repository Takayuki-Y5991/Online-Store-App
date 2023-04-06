package com.konkon.onlinestore.product.search.service.domain.usecase;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

public interface ProductUseCase {
    Uni<Product> searchProduct(UUID productId);

    Multi<Product> searchProducts(String key, String order, Integer limit, Integer offset);
}
