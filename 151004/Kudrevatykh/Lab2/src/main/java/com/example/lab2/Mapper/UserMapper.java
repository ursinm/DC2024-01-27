package com.example.lab2.Mapper;

import com.example.lab2.DTO.UserRequestTo;
import com.example.lab2.DTO.UserResponseTo;
import com.example.lab2.Model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userRequestToUser(UserRequestTo userRequestTo);

    UserResponseTo userToUserResponse(User user);
}
