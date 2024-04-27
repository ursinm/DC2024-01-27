package com.example.lab1.Mapper;

import com.example.lab1.DTO.UserRequestTo;
import com.example.lab1.DTO.UserResponseTo;
import com.example.lab1.Model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserListMapper {
    List<User> toUserList(List<UserRequestTo> users);

    List<UserResponseTo> toUserResponseList(List<User> users);
}
