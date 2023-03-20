package com.konkon.onlinestore.product.search.service.application.extensions;

import com.konkon.onlinestore.product.search.service.application.ProductSearchUserCase;
import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.service.ProductSearchService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ProductSearchUseCase implements ProductSearchUserCase {

    private final ProductSearchService productSearchService;

    @Inject
    public ProductSearchUseCase(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    public List<Product> searchProducts(String query) {
        return productSearchService.searchProducts(query);
    }
}
