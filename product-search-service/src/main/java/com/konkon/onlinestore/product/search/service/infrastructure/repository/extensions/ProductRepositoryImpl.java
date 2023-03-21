package com.konkon.onlinestore.product.search.service.infrastructure.repository.extensions;

import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.value.ProductId;
import com.konkon.onlinestore.product.search.service.infrastructure.entity.ProductEntity;
import com.konkon.onlinestore.product.search.service.infrastructure.mapper.ProductMapper;
import com.konkon.onlinestore.product.search.service.infrastructure.repository.ProductRepository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class ProductRepositoryImpl implements PanacheRepository<ProductEntity>, ProductRepository {

    private final ProductMapper productMapper;

    @Inject
    public ProductRepositoryImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }


    @Override
    public Uni<Product> searchProduct(ProductId productId) {
        var result = find("productId", productId).firstResult();
        return result.map(productMapper::toDomain);
    }

    @Override
    public Multi<Product> searchProducts(Map<String, String> query) {
        return null;
    }

    @Override
    public Uni<Product> createProduct(Product product) {
        return null;
    }

    @Override
    public Uni<Boolean> deleteProduct(UUID uuid) {
        return null;
    }

    @Override
    public Uni<Product> updateProduct(Product product) {
        return null;
    }
}
