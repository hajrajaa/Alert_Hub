package com.mst.actionservice.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = ConditionsValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidConditions {

    String message() default "conditions must be a non-empty list of integer lists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
