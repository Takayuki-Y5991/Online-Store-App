package com.konkon.onlinestore.product.search.service.application.rest.model.request;

import javax.validation.constraints.Negative;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public record UpdateProductRequest(
        @NotNull
        @Size(max = 100)
        String name,
        @Negative
        BigDecimal price,
        @NotNull
        String description,
        @Null
        @Size(max = 255)
        String imageUrl,
        @Negative
        @NotNull
        int version,
        @Negative
        @NotNull
        Integer categoryId
) {
}
