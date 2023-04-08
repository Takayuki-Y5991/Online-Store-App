package com.konkon.onlinestore.product.search.service.utils.annotation;

import com.konkon.onlinestore.product.search.service.utils.annotation.extensions.OrderConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = OrderConstraintValidator.class)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderConstraint {
    String message() default "Order Param is valid, must be `asc` or `desc`";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
