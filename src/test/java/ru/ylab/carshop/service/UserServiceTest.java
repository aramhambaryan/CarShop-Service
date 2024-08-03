package ru.ylab.carshop.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.carshop.domain.dto.out.GetUserOut;
import ru.ylab.carshop.domain.entity.User;
import ru.ylab.carshop.domain.enums.UserRole;
import ru.ylab.carshop.domain.exception.UserNotFoundException;
import ru.ylab.carshop.mapper.UserMapper;
import ru.ylab.carshop.mapper.UserMapperImpl;
import ru.ylab.carshop.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Spy
    private UserMapper userMapper = new UserMapperImpl();
    @InjectMocks
    private UserService userService;


    @Test
    void getByUsername_whenUserNotExist_thenThrow() {
        String username = "testUsername";
        when(userRepository.findByUsernameThrowing(username)).thenThrow(new UserNotFoundException(username));
        assertThrows(UserNotFoundException.class, () -> userService.getByUsername(username));
    }

    @Test
    void getByUsername_whenExist_ReturnIt() {
        User user = getRandomUser();
        when(userRepository.findByUsernameThrowing(user.getUsername())).thenReturn(user);
        GetUserOut getUserOut = userService.getByUsername(user.getUsername());

        assertEquals(user.getUsername(), getUserOut.getUsername());
        assertEquals(user.getId(), getUserOut.getId());
        assertEquals(user.getRole(), getUserOut.getRole());
    }

    @Test
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
    void whenDelete_ThenCallDeleteOnRepo() {
        String randomUsername = RandomStringUtils.random(10);
        userService.deleteUser(randomUsername);
        verify(userRepository, atLeastOnce()).deleteByUsername(randomUsername);
    }

    private User getRandomUser() {
       return User.builder()
                .username(RandomStringUtils.random(10, "abcdefghijkl"))
                .id(ThreadLocalRandom.current().nextLong())
                .role(UserRole.values()[ThreadLocalRandom.current().nextInt(UserRole.values().length)])
                .build();
    }
}