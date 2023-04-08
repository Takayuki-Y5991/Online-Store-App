package com.konkon.onlinestore.product.search.service.utils.annotation.extensions;

import com.konkon.onlinestore.product.search.service.utils.annotation.ProductSortKeyConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

public class ProductSortKeyConstraintValidator implements ConstraintValidator<ProductSortKeyConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) return true;
        String key = value.toUpperCase();
        return Arrays.asList("p.id", "p.name", "price", "c.id").contains(key);
    }
}
