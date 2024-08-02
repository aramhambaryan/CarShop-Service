package ru.ylab.carshop.domain.dto.out;

import lombok.Getter;
import lombok.Setter;
import ru.ylab.carshop.domain.enums.UserRole;

@Getter
@Setter
public class GetUserOut {

    private Long id;
    private String username;
    private UserRole role;
}
