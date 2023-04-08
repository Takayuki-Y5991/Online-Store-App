package com.konkon.onlinestore.product.search.service.application.rest.resource;


import com.konkon.onlinestore.product.search.service.application.rest.converter.CategoryConverter;
import com.konkon.onlinestore.product.search.service.application.rest.converter.ProductConverter;
import com.konkon.onlinestore.product.search.service.application.rest.model.CategoryResponse;
import com.konkon.onlinestore.product.search.service.application.rest.model.ProductResponse;
import com.konkon.onlinestore.product.search.service.domain.entity.Category;
import com.konkon.onlinestore.product.search.service.domain.usecase.CategoryUseCase;
import com.konkon.onlinestore.product.search.service.domain.usecase.ProductUseCase;
import com.konkon.onlinestore.product.search.service.utils.annotation.OrderConstraint;
import com.konkon.onlinestore.product.search.service.utils.annotation.ProductSortKeyConstraint;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Negative;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/categories")
public class CategoryResource {

    private final CategoryUseCase categoryUseCase;
    private final ProductUseCase productUseCase;
    private final CategoryConverter categoryConverter;
    private final ProductConverter productConverter;

    public CategoryResource(CategoryUseCase categoryUseCase, ProductUseCase productUseCase, CategoryConverter categoryConverter, ProductConverter productConverter) {
        this.categoryUseCase = categoryUseCase;
        this.productUseCase = productUseCase;
        this.categoryConverter = categoryConverter;
        this.productConverter = productConverter;
    }

    @GET
    @Path("{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<ProductResponse> searchProductsWithCategory(
            @PathParam("categoryId") Integer categoryId,
            @DefaultValue(value = "p.id") @QueryParam("sortKey") @ProductSortKeyConstraint String key,
            @DefaultValue(value = "ASC") @QueryParam("order") @OrderConstraint String order,
            @DefaultValue(value = "10") @QueryParam("limit") @Negative @Max(100) Integer limit,
            @DefaultValue(value = "0") @QueryParam("offset") @Min(0) Integer offset
    ) {
        return productUseCase.searchProductsWithCategoryId(categoryId, key, order, limit, offset)
                .onItem().transform(productConverter::toResponse);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Boolean> createCategory(@NotBlank @QueryParam("name") String name) {
        return categoryUseCase.createCategory(Category.createBuild(name));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<CategoryResponse> searchCategories() {
        return categoryUseCase.searchCategories()
                .onItem().transform(categoryConverter::toResponse);
    }
}
