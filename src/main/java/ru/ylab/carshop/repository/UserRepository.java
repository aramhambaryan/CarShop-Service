package ru.ylab.carshop.repository;

import ru.ylab.carshop.domain.entity.User;
import ru.ylab.carshop.domain.exception.UserNotFoundException;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepository {

    private final Map<Long, User> userMap;
    private static final AtomicLong idGenerator = new AtomicLong();

    public UserRepository() {
        this.userMap = new HashMap<>();
    }

    /**
     * add or replace the existing user (identified by {@link User#getUsername()}  )
     * @throws NullPointerException if the argument is null
     * @param user the user to save
     */
    public Long save(User user) {
        Objects.requireNonNull(user);
        Long id = findByUsername(user.getUsername())
                .map(User::getId)
                .orElseGet(idGenerator::incrementAndGet);
        User newUser = user.toBuilder().id(id).build();
        userMap.put(id, newUser);
        return id;
    }

    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    public Optional<User> findByUsername(String username) {
        return userMap.entrySet().stream()
                .filter(x -> Objects.equals(x.getValue().getUsername(), username))
                .findAny()
                .map(x -> x.getValue().toBuilder().build());
    }

    /**
     * find user by username
     * @param username username to find user by
     * @return the found user
     * @throws UserNotFoundException if user not found
     */
    public User findByUsernameThrowing(String username) {
        return findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public List<User> findAll() {
        return userMap.values().stream()
                .map(user -> user.toBuilder().build())
                .toList();
    }

    /**
     * delete user by given username
     * @param username username by which to delete
     * @return {@link Optional<User>} that has been deleted, otherwise empty optional
     */
    public Optional<User> deleteByUsername(String username) {
        return findByUsername(username)
                .map(x -> x.toBuilder().build());
    }

}
