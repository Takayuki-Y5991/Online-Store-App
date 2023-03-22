package com.konkon.onlinestore.product.search.service.domain.value;

import java.util.Objects;

public record FullName(String firstName, String lastName) {
    public FullName {
        if (Objects.isNull(firstName) | Objects.isNull(lastName)) {
            throw new IllegalArgumentException("Name must be null");
        }
    }
}
