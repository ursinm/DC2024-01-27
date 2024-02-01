package com.poluectov.rvlab1.utils.dtoconverter;

import com.poluectov.rvlab1.dto.user.UserResponseTo;
import com.poluectov.rvlab1.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserResponseDtoConverter implements DtoConverter<User, UserResponseTo> {

    @Override
    public UserResponseTo convert(User user) {
        return UserResponseTo.builder()
                .id(user.getId())
                .login(user.getLogin())
                .hashedPassword(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
