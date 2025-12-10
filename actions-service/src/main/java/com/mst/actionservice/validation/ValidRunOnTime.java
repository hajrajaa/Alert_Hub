package com.mst.actionservice.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RunOnTimeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRunOnTime  {

    String message() default "Invalid Run On Time Format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
