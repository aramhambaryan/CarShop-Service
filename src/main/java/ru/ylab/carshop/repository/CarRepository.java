package ru.ylab.carshop.repository;

import ru.ylab.carshop.domain.entity.Car;
import ru.ylab.carshop.domain.exception.CarNotFoundException;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

public class CarRepository {

    private final Map<Long, Car> carsMap;
    private static final AtomicLong idGenerator = new AtomicLong(0);

    public CarRepository() {
        this.carsMap = new HashMap<>();
    }

    public List<Car> findAll() {
        return carsMap.values().stream()
                .map(x -> x.toBuilder().build())
                .toList();
    }

    /**
     * find cars that satisfy the given predicate
     * @param predicate the predicate to filter cars by
     * @return list of cars
     */
    public List<Car> findByFilter(Predicate<Car> predicate) {
        return carsMap.values().stream()
                .filter(predicate)
                .toList();
    }

    /**
     * add or update a car (create when id is null, update when id nonnull).
     * @throws CarNotFoundException if car with given {@link Car#getId()} does not exist
     * @return id of created or updated car
     */
    public Long save(Car car) {
        Objects.requireNonNull(car);
        if (car.getId() == null) {
            car = car.toBuilder().id(idGenerator.incrementAndGet()).build();
        } else {
            car = Car.builder().build();
        }
        carsMap.put(car.getId(), car);
        return car.getId();
    }

    /**
     *
     * @param id id to find Car by
     * @return Car with given id
     * @throws CarNotFoundException if a car with given id does not exist
     * @throws NullPointerException if id is null
     */
    public Car findByIdThrowing(Long id) {
        return findById(id).orElseThrow(() -> new CarNotFoundException(id));
    }

    /**
     * @throws NullPointerException if the given id is null
     * @return Optional containing the found car or an empty Optional if not found
     */
    public Optional<Car> findById(Long id) {
        Objects.requireNonNull(id);
        return Optional.ofNullable(carsMap.get(id))
                .map(x -> x.toBuilder().build());
    }

    /**
     *
     * @return true if car exists with given id
     * @throws NullPointerException if given id is null
     */
    public boolean existsById(Long id) {
        Objects.requireNonNull(id);
        return carsMap.containsKey(id);
    }

}
