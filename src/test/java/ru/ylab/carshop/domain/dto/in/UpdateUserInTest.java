package ru.ylab.carshop.domain.dto.in;

import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.ylab.carshop.util.TestUtil.validatorSupplier;

class UpdateUserInTest {

    private final String passwordFieldName = "password";

    @Test
    @DisplayName("assert no violation is detected when all fields are null")
    void whenNull_noViolation() {
        Validator validator = validatorSupplier.get();
        UpdateUserIn updateUserIn = new UpdateUserIn();
        var violations = validator.validateProperty(updateUserIn, passwordFieldName);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("violate when an empty optional is set for password")
    void whenEmptyOptionalForPassword_violationExists() {
        Validator validator = validatorSupplier.get();
        UpdateUserIn updateUserIn = new UpdateUserIn();
        updateUserIn.setPassword(Optional.empty());
        var violations = validator.validateProperty(updateUserIn, passwordFieldName);
        assertTrue(violations.size() > 0);
    }

    @Test
    @DisplayName("no violation when a non empty Optional is set for the password field")
    void whenNotEmptyOptional_noViolation() {
        Validator validator = validatorSupplier.get();
        UpdateUserIn updateUserIn = new UpdateUserIn();
        updateUserIn.setPassword(Optional.of("testPassword"));
        var violations = validator.validateProperty(updateUserIn, passwordFieldName);
        assertTrue(violations.isEmpty());
    }

}