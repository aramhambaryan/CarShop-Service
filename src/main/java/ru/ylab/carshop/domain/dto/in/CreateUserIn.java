package ru.ylab.carshop.domain.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.ylab.carshop.domain.enums.UserRole;

@Getter
@Setter
public class CreateUserIn {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotNull
    private UserRole role;
}
