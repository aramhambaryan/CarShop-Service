package ru.ylab.carshop.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
class ValidationUtilTest {

    @Mock
    private Set<ConstraintViolation<Object>> violationSetMock;
    @Mock
    private Validator validatorMock;


    @Test
    void validateWithException() {
        Mockito.when(violationSetMock.isEmpty()).thenReturn(false);
        Mockito.when(validatorMock.validate(Mockito.any())).thenReturn(violationSetMock);

        ValidationUtil validationUtil = new ValidationUtil(validatorMock);
        Assertions.assertThrows(Throwable.class, () -> validationUtil.validateWithException(new Object()));
    }
}