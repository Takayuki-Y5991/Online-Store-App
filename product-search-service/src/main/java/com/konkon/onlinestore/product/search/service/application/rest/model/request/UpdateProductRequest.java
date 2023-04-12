package com.konkon.onlinestore.product.search.service.application.rest.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public record UpdateProductRequest(
        @NotNull
        @Size(max = 100)
        String name,
        @Min(1)
        BigDecimal price,
        @NotNull
        String description,
        @Size(max = 255)
        String imageUrl,
        @Min(1)
        @NotNull
        int version,
        @Min(1)
        @NotNull
        Integer categoryId
) {
}
