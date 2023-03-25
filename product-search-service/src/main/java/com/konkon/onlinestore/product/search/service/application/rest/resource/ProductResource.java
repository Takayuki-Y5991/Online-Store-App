package com.konkon.onlinestore.product.search.service.application.rest.resource;

import com.konkon.onlinestore.product.search.service.application.rest.converter.ProductConverter;
import com.konkon.onlinestore.product.search.service.application.rest.model.ProductResponse;
import com.konkon.onlinestore.product.search.service.domain.usecase.ProductUseCase;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/products")
public class ProductResource {

    private final ProductUseCase productUseCase;
    private final ProductConverter productConverter;

    @Inject
    public ProductResource(ProductUseCase productUseCase, ProductConverter productConverter) {
        this.productUseCase = productUseCase;
        this.productConverter = productConverter;
    }

    @GET
    @Path("/productId")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ProductResponse> searchProducts(@PathParam("productId") String productId) {
        return productUseCase.searchProduct(UUID.fromString(productId))
                .onItem().transform(null);

    }
}
