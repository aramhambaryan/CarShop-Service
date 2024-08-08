package ru.ylab.carshop.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.ylab.carshop.domain.enums.UserRole;

/**
 * User class representing a user in the system.
 */
@Getter
@Setter
@Builder(toBuilder = true)
public class User {

    private Long id;
    private String username;
    private String password;
    private UserRole role;
}
