package com.konkon.onlinestore.product.search.service.utils.annotation.extensions;

import com.konkon.onlinestore.product.search.service.utils.annotation.UUIDConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class UUIDConstraintValidator implements ConstraintValidator<UUIDConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) return false;

        try {
            java.util.UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
