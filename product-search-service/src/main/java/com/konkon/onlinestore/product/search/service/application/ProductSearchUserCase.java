package com.konkon.onlinestore.product.search.service.application;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;

import java.util.List;

public interface ProductSearchUserCase {
    List<Product> searchProducts(String query);
}
