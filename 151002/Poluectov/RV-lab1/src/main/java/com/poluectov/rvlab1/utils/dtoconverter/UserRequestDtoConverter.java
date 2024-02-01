package com.poluectov.rvlab1.utils.dtoconverter;

import com.poluectov.rvlab1.dto.user.UserRequestTo;
import com.poluectov.rvlab1.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestDtoConverter implements DtoConverter<UserRequestTo, User> {

    @Override
    public User convert(UserRequestTo userRequestTo) {
        return User.builder()
                .id(userRequestTo.getId())
                .login(userRequestTo.getLogin())
                .password(userRequestTo.getPassword()) // TODO: Hash password
                .firstName(userRequestTo.getFirstName())
                .lastName(userRequestTo.getLastName())
                .build();
    }
}
