package ru.ylab.carshop.domain.dto.in;

import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.ylab.carshop.util.TestUtil.validatorSupplier;

class UpdateUserInTest {

    private final String passwordFieldName = "password";

    @Test
    void whenNull_NoViolation() {
        Validator validator = validatorSupplier.get();
        UpdateUserIn updateUserIn = new UpdateUserIn();
        var violations = validator.validateProperty(updateUserIn, passwordFieldName);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenEmptyOptional_ViolationExists() {
        Validator validator = validatorSupplier.get();
        UpdateUserIn updateUserIn = new UpdateUserIn();
        updateUserIn.setPassword(Optional.empty());
        var violations = validator.validateProperty(updateUserIn, passwordFieldName);
        assertTrue(violations.size() > 0);
    }

    @Test
    void whenNotEmptyOptional_NoViolation() {
        Validator validator = validatorSupplier.get();
        UpdateUserIn updateUserIn = new UpdateUserIn();
        updateUserIn.setPassword(Optional.of("testPassword"));
        var violations = validator.validateProperty(updateUserIn, passwordFieldName);
        assertTrue(violations.isEmpty());
    }

}