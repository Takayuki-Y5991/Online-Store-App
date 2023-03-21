package com.konkon.onlinestore.product.search.service.domain.usecase;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.value.ProductId;
import io.smallrye.mutiny.Uni;

public interface ProductUseCase {
    Uni<Product> searchProduct(ProductId productId);
}
