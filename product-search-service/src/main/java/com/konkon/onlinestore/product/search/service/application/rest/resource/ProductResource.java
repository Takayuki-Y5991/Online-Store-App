package com.konkon.onlinestore.product.search.service.application.rest.resource;

import com.konkon.onlinestore.product.search.service.application.rest.converter.ProductConverter;
import com.konkon.onlinestore.product.search.service.application.rest.converter.ProductReviewConverter;
import com.konkon.onlinestore.product.search.service.application.rest.model.CreateProductRequest;
import com.konkon.onlinestore.product.search.service.application.rest.model.ProductResponse;
import com.konkon.onlinestore.product.search.service.application.rest.model.ProductReviewResponse;
import com.konkon.onlinestore.product.search.service.application.rest.model.UpdateProductRequest;
import com.konkon.onlinestore.product.search.service.domain.usecase.ProductUseCase;
import com.konkon.onlinestore.product.search.service.utils.annotation.OrderConstraint;
import com.konkon.onlinestore.product.search.service.utils.annotation.ProductSortKeyConstraint;
import com.konkon.onlinestore.product.search.service.utils.annotation.UUIDConstraint;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.resteasy.reactive.ResponseStatus;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Negative;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/products")
public class ProductResource {

    private final ProductUseCase productUseCase;
    private final ProductConverter productConverter;
    private final ProductReviewConverter productReviewConverter;

    @Inject
    public ProductResource(ProductUseCase productUseCase, ProductConverter productConverter, ProductReviewConverter productReviewConverter) {
        this.productUseCase = productUseCase;
        this.productConverter = productConverter;
        this.productReviewConverter = productReviewConverter;
    }

    @GET
    @Path("{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ProductReviewResponse> searchProduct(@PathParam("productId") @UUIDConstraint String productId) {
        return productUseCase.searchProduct(UUID.fromString(productId))
                .onItem().transform(productReviewConverter::toResponse);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<ProductResponse> searchProducts(
            @DefaultValue(value = "p.id") @QueryParam("sortKey") @ProductSortKeyConstraint String key,
            @DefaultValue(value = "ASC") @QueryParam("order") @OrderConstraint String order,
            @DefaultValue(value = "10") @QueryParam("limit") @Negative @Max(100) Integer limit,
            @DefaultValue(value = "0") @QueryParam("offset") @Min(0) Integer offset) {
        return productUseCase.searchProducts(key, order, limit, offset)
                .onItem().transform(productConverter::toResponse);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    public Uni<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        return productUseCase.createProduct(productConverter.toDomain(request))
                .onItem().transform(productConverter::toResponse);
    }

    @PUT
    @Path("{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(204)
    public Uni<ProductResponse> updateProduct(@PathParam("productId") @UUIDConstraint String productId, @Valid @RequestBody UpdateProductRequest request) {
        return productUseCase.updateProduct(productConverter.toDomain(UUID.fromString(productId), request))
                .onItem().transform(productConverter::toResponse);
    }

    @DELETE
    @Path("{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(204)
    public Uni<Boolean> deleteProduct(@PathParam("productId") @UUIDConstraint String productId) {
        return productUseCase.deleteProduct(UUID.fromString(productId));
    }
}
