package com.konkon.onlinestore.product.search.service.domain.usecase.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.usecase.ProductUseCase;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.ProductRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.UUID;

@ApplicationScoped
public class ProductUseCaseImpl implements ProductUseCase {

    private final ProductRepository productRepository;

    @Inject
    public ProductUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Uni<Product> searchProduct(UUID productId) {
        return productRepository.searchProduct(productId)
                .onItem().ifNull().failWith(() -> new NotFoundException("Product not found"));
    }

    @Override
    public Multi<Product> searchProducts(String key, String order, Integer limit, Integer offset) {
        return productRepository.searchProducts(key, order, limit, offset);
    }
}
