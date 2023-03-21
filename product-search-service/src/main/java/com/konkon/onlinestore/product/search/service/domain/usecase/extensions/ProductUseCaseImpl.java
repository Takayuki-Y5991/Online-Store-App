package com.konkon.onlinestore.product.search.service.domain.usecase.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.usecase.ProductUseCase;
import com.konkon.onlinestore.product.search.service.domain.value.ProductId;
import com.konkon.onlinestore.product.search.service.infrastructure.repository.ProductRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ProductUseCaseImpl implements ProductUseCase {

    private final ProductRepository productRepository;

    @Inject
    public ProductUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Uni<Product> searchProduct(ProductId productId) {
        return productRepository.searchProduct(productId);
    }
}
