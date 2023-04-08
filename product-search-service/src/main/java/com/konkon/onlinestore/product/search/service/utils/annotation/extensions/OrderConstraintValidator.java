package com.konkon.onlinestore.product.search.service.utils.annotation.extensions;

import com.konkon.onlinestore.product.search.service.utils.annotation.OrderConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

public class OrderConstraintValidator implements ConstraintValidator<OrderConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) return true;
        String target = value.toUpperCase();
        return Arrays.asList("ASC", "DESC").contains(target);
    }
}
