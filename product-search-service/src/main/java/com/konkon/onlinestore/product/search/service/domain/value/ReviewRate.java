package com.konkon.onlinestore.product.search.service.domain.value;

import java.util.Objects;

public record ReviewRate(Integer value) {
    public ReviewRate {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("Review rate must be No Item");
        }
        if (value < 1 & 5 < value) {
            throw new IllegalArgumentException("Please rate your review on a scale of 1-5.");
        }
    }
}
