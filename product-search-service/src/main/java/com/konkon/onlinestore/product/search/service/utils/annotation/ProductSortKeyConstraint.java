package com.konkon.onlinestore.product.search.service.utils.annotation;

import com.konkon.onlinestore.product.search.service.utils.annotation.extensions.ProductSortKeyConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ProductSortKeyConstraintValidator.class)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductSortKeyConstraint {
    String message() default "Product sort key is valid, must be [p.id, p.name, price, c.id]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
