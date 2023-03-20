package com.konkon.onlinestore.product.search.service.domain.service;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;

import java.util.List;

public interface ProductSearchService {

    List<Product> searchProducts(String query);
}
