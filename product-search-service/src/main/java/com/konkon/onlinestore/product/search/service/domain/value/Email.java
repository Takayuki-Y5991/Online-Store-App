package com.konkon.onlinestore.product.search.service.domain.value;

import java.util.Objects;

public record Email(String value) {
    public Email {
        if (Objects.isNull(value) | !value.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@(\\w+\\.)+\\w+\\w$")) {
            throw new IllegalArgumentException("Invalid email Address");
        }
    }
}
