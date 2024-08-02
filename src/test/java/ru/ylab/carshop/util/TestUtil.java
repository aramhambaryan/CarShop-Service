package ru.ylab.carshop.util;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.function.Supplier;

public class TestUtil {

    public static final Supplier<Validator> validatorSupplier = () -> {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            return validatorFactory.getValidator();
        }
    };

}
