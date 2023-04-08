package com.konkon.onlinestore.product.search.service.application.rest.converter.extensions;

import com.konkon.onlinestore.product.search.service.application.rest.converter.CategoryConverter;
import com.konkon.onlinestore.product.search.service.application.rest.converter.ProductConverter;
import com.konkon.onlinestore.product.search.service.application.rest.model.CreateProductRequest;
import com.konkon.onlinestore.product.search.service.application.rest.model.ProductResponse;
import com.konkon.onlinestore.product.search.service.application.rest.model.UpdateProductRequest;
import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import com.konkon.onlinestore.product.search.service.domain.entity.Product;
import com.konkon.onlinestore.product.search.service.domain.value.Price;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class ProductConverterImpl implements ProductConverter {

    private final CategoryConverter categoryConverter;

    @Inject
    public ProductConverterImpl(CategoryConverter categoryConverter) {
        this.categoryConverter = categoryConverter;
    }

    @Override
    public ProductResponse toResponse(Product domain) {
        return new ProductResponse(
                domain.getId(),
                domain.getName(),
                domain.getPrice().value(),
                domain.getDescription(),
                domain.getImageUrl(),
                domain.getVersion(),
                categoryConverter.toResponse(domain.getCategory()));
    }

    @Override
    public Product toDomain(CreateProductRequest request) {
        return Product.createBuild(
                request.name(),
                request.description(),
                new Price(request.price()),
                request.imageUrl(),
                Category.createBuild(request.categoryId())
        );
    }

    @Override
    public Product toDomain(UUID productId, UpdateProductRequest request) {
        return Product.build(
                productId,
                request.name(),
                request.description(),
                new Price(request.price()),
                request.imageUrl(),
                request.version(),
                Category.createBuild(request.categoryId())
        );
    }
}
