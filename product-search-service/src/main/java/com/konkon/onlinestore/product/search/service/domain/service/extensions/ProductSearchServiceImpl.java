package com.konkon.onlinestore.product.search.service.domain.service.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.service.ProductSearchService;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProductSearchServiceImpl implements ProductSearchService {
    @Override
    public List<Product> searchProducts(String query) {
        return null;
    }
}
