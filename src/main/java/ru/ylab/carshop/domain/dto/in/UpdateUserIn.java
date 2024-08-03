package ru.ylab.carshop.domain.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.ylab.carshop.domain.enums.UserRole;

import java.util.Optional;

@Setter
@Getter
public class UpdateUserIn {

    private Optional<@NotEmpty String> password;
    private Optional<@NotNull UserRole> role;
}
