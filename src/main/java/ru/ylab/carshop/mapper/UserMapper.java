package ru.ylab.carshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ylab.carshop.domain.dto.in.CreateUserIn;
import ru.ylab.carshop.domain.dto.in.UpdateUserIn;
import ru.ylab.carshop.domain.dto.out.GetUserOut;
import ru.ylab.carshop.domain.entity.User;

import java.util.Optional;
import java.util.function.Function;

@Mapper
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toUser(CreateUserIn createUserIn);
    GetUserOut toGetUserOut(User user);

    default void merge(UpdateUserIn from, User into) {
        Optional.ofNullable(from.getRole())
                .flatMap(Function.identity())
                .ifPresent(into::setRole);
    }
}
