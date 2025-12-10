package com.mst.actionservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailOrPhoneValidator implements ConstraintValidator<EmailOrPhone, String> {

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern PHONE_REGEX =
            Pattern.compile("^\\+?[0-9]{10,15}$"); // simple international phone pattern

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;
        return EMAIL_REGEX.matcher(value).matches() || PHONE_REGEX.matcher(value).matches();
    }
}
