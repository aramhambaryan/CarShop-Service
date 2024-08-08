package ru.ylab.carshop.domain.exception;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(Long id) {
        super(String.format("Car with id '%s' not found", id));
    }
}
