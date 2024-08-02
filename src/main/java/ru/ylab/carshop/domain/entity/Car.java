package ru.ylab.carshop.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Car {

    private String brand;
    private String model;
    private Integer year;
    private String condition;
    private Double price;

}
