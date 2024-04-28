package com.example.lab1.Mapper;

import com.example.lab1.DTO.UserRequestTo;
import com.example.lab1.DTO.UserResponseTo;
import com.example.lab1.Model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userRequestToUser(UserRequestTo userRequestTo);

    UserResponseTo userToUserResponse(User user);
}
