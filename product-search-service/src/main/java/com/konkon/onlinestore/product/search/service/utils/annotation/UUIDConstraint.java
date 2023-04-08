package com.konkon.onlinestore.product.search.service.utils.annotation;

import com.konkon.onlinestore.product.search.service.utils.annotation.extensions.UUIDConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UUIDConstraintValidator.class)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UUIDConstraint {

    String message() default "Parameter is Valid, must be UUID";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
