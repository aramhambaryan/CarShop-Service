package ru.ylab.carshop.domain.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizeUserIn {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
