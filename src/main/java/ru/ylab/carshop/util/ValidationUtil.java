package ru.ylab.carshop.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class ValidationUtil {

    private final Validator validator;

    public <T> void validateWithException(T toValidate) {
        Set<ConstraintViolation<T>> result = validator.validate(toValidate);
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
    }
}
