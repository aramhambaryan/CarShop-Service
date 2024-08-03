package ru.ylab.carshop.service;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import ru.ylab.carshop.domain.dto.in.AuthorizeUserIn;
import ru.ylab.carshop.domain.dto.in.CreateUserIn;
import ru.ylab.carshop.domain.dto.in.UpdateUserIn;
import ru.ylab.carshop.domain.dto.out.GetUserOut;
import ru.ylab.carshop.domain.entity.User;
import ru.ylab.carshop.domain.exception.DuplicateUsernameException;
import ru.ylab.carshop.domain.exception.UserNotFoundException;
import ru.ylab.carshop.mapper.UserMapper;
import ru.ylab.carshop.repository.UserRepository;
import ru.ylab.carshop.util.ValidationUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;


    /**
     * create/add a new user
     * @param createUserIn contains fields for user to create
     * @return id of newly created user
     * @throws DuplicateUsernameException if username is already taken
     * @throws ConstraintViolationException if argument's fields does not meet the constraints set by annotations on them
     */
    public Long addUser(CreateUserIn createUserIn) {
        validationUtil.validateWithException(createUserIn);
        if (userRepository.existsByUsername(createUserIn.getUsername())) {
            throw new DuplicateUsernameException(createUserIn.getUsername());
        }

        User user = userMapper.toUser(createUserIn);
        return userRepository.save(user);
    }

    /**
     * @return list of all users
     */
    public List<GetUserOut> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toGetUserOut)
                .toList();
    }

    /**
     * find user by username
     * @param username username to find user by
     * @return the user if exists
     * @throws UserNotFoundException if user not found
     */
    public GetUserOut getByUsername(String username) {
        Objects.requireNonNull(username);
        User user = userRepository.findByUsernameThrowing(username);
        return userMapper.toGetUserOut(user);
    }

    /**
     * update existing user
     * @param updateUserIn argument containing fields to update
     * @param username username to find user by
     * @throws ConstraintViolationException if annotation constraints not met (set on fields of argument's class)
     * @throws UserNotFoundException if user with given username not exists
     */
    public void updateUser(UpdateUserIn updateUserIn, String username) {
        Objects.requireNonNull(username);
        validationUtil.validateWithException(updateUserIn);
        User user = userRepository.findByUsernameThrowing(username);
        userMapper.merge(updateUserIn, user);
        userRepository.save(user);
    }

    /**
     * delete user by username
     * @param username the username to delete by
     * @return {@link Optional<User>} value containing deleted user.
     * If user not found returns and empty optional
     */
    public Optional<User> deleteUser(String username) {
        Objects.requireNonNull(username);
        return userRepository.deleteByUsername(username);
    }

    public boolean authorizeUser(AuthorizeUserIn authorizeUserIn) {
        validationUtil.validateWithException(authorizeUserIn);
        return userRepository.findByUsername(authorizeUserIn.getUsername())
                .map(user -> user.getPassword().equals(authorizeUserIn.getPassword()))
                .orElse(false);
    }
}
