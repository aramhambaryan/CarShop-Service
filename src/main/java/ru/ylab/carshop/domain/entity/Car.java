package ru.ylab.carshop.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an available car with fields describing its specifics
 */
@Setter
@Getter
@Builder(toBuilder = true)
public class Car {

    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private String condition;
    private Double price;

}
