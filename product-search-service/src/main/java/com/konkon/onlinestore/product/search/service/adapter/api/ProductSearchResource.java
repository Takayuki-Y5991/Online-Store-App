package com.konkon.onlinestore.product.search.service.adapter.api;

import com.konkon.onlinestore.product.search.service.application.ProductSearchUserCase;
import com.konkon.onlinestore.product.search.service.presentation.dto.ProductDto;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.awt.*;

@Path("/products")
public class ProductSearchResource {

    private final ProductSearchUserCase productSearchUserCase;

    @Inject
    public ProductSearchResource(ProductSearchUserCase productSearchUserCase) {
        this.productSearchUserCase = productSearchUserCase;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductDto> searchProducts(@QueryParam("query") String query) {
        return ;
    }
}
