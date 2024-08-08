package ru.ylab.carshop.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.carshop.domain.dto.in.AuthorizeUserIn;
import ru.ylab.carshop.domain.dto.out.GetUserOut;
import ru.ylab.carshop.domain.entity.User;
import ru.ylab.carshop.domain.enums.UserRole;
import ru.ylab.carshop.domain.exception.UserNotFoundException;
import ru.ylab.carshop.mapper.UserMapper;
import ru.ylab.carshop.mapper.UserMapperImpl;
import ru.ylab.carshop.repository.UserRepository;
import ru.ylab.carshop.util.ValidationUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Spy
    private UserMapper userMapper = new UserMapperImpl();
    @Mock
    private ValidationUtil validationUtil;
    @InjectMocks
    private UserService userService;
    private final int randomCredentialLength = 10;


    @Test
    @DisplayName("assert UserNotFoundException is thrown when username not found during userService.getByUsername call")
    void getByUsername_whenUserNotExist_thenThrow() {
        String username = "testUsername";
        when(userRepository.findByUsernameThrowing(username)).thenThrow(new UserNotFoundException(username));
        assertThrows(UserNotFoundException.class, () -> userService.getByUsername(username));
    }

    @Test
    @DisplayName("assert found username is returned when calling userService.getByUsername")
    void getByUsername_whenExist_ReturnIt() {
        User user = getRandomUser();
        when(userRepository.findByUsernameThrowing(user.getUsername())).thenReturn(user);
        GetUserOut getUserOut = userService.getByUsername(user.getUsername());

        assertEquals(user.getUsername(), getUserOut.getUsername());
        assertEquals(user.getId(), getUserOut.getId());
        assertEquals(user.getRole(), getUserOut.getRole());
    }

    @Test
    @DisplayName("assert all users are returned when calling userService.getAllUsers()")
    void getAllUsers_whenNotEmpty_returnAll() {
        List<User> expected = IntStream.range(1, 10)
                .mapToObj(x -> getRandomUser())
                .toList();
        when(userRepository.findAll()).thenReturn(expected);
        List<GetUserOut> actual = userService.getAllUsers();

        assertEquals(expected.size(), actual.size());
        expected.stream().map(x -> actual.stream().anyMatch(y -> Objects.equals(x.getUsername(), y.getUsername())
                        && Objects.equals(x.getId(), y.getId())
                        && Objects.equals(x.getRole(), y.getRole())
                )
        ).forEach(Assertions::assertTrue);
    }

    @Test
    @DisplayName("when calling userService.deleteUser assert userRepository.deleteByUsername is called " +
            "with the provided username")
    void whenDelete_thenCallDeleteOnRepo() {
        String randomUsername = RandomStringUtils.random(randomCredentialLength);
        userService.deleteUser(randomUsername);
        verify(userRepository, atLeastOnce()).deleteByUsername(randomUsername);
    }

    @Test
    @DisplayName("assert call userService.authorizeUser returns true when a match is found in repo")
    void authorizeUser_whenMatchFound_returnTrue() {
        AuthorizeUserIn authorizeUserIn = getRandomAuthorizeUserIn();
        Optional<User> user = Optional.of(userMapper.toUser(authorizeUserIn));
        when(userRepository.findByUsername(authorizeUserIn.getUsername())).thenReturn(user);
        doNothing().when(validationUtil).validateWithException(authorizeUserIn);
        assertTrue(userService.authorizeUser(authorizeUserIn));
    }

    private AuthorizeUserIn getRandomAuthorizeUserIn() {
        return AuthorizeUserIn.builder()
                .username(RandomStringUtils.random(randomCredentialLength))
                .password(RandomStringUtils.random(randomCredentialLength))
                .build();
    }

    private User getRandomUser() {
       return User.builder()
                .username(RandomStringUtils.randomAlphabetic(randomCredentialLength))
                .id(ThreadLocalRandom.current().nextLong())
                .role(UserRole.values()[ThreadLocalRandom.current().nextInt(UserRole.values().length)])
                .build();
    }
}