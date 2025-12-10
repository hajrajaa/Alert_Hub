package com.mst.actionservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class RunOnTimeValidator implements ConstraintValidator<ValidRunOnTime, LocalTime> {

    @Override
    public boolean isValid(LocalTime time, ConstraintValidatorContext context)
    {
        if (time == null)
        {
            return true;
        }
        int minute = time.getMinute();

        // ensure minutes 00 or 30
        return (minute==00 || minute==30);
    }
}
