package com.konkon.onlinestore.product.search.service.domain.usecase.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.usecase.ProductUseCase;
import com.konkon.onlinestore.product.search.service.infrastructure.datasource.repository.ProductRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.UUID;

@ApplicationScoped
public class ProductUseCaseImpl implements ProductUseCase {

    private final ProductRepository productRepository;
    private final PgPool client;

    @Inject
    public ProductUseCaseImpl(ProductRepository productRepository, PgPool client) {
        this.productRepository = productRepository;
        this.client = client;
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

    @Override
    public Uni<Product> createProduct(Product product) {
        return client.withTransaction(conn ->
                productRepository.createProduct(product, conn)
        );
    }

    @Override
    public Uni<Product> updateProduct(Product product) {
        return client.withTransaction(conn ->
                productRepository.updateProduct(product, conn));
    }

    @Override
    public Uni<Boolean> deleteProduct(UUID productId) {
        return client.withTransaction(conn ->
                productRepository.deleteProduct(productId, conn));
    }
}
