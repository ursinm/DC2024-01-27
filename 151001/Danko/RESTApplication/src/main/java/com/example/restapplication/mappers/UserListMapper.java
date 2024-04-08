package com.example.restapplication.mappers;

import com.example.restapplication.dto.UserRequestTo;
import com.example.restapplication.dto.UserResponseTo;
import com.example.restapplication.entites.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserListMapper {
    List<User> toUserList(List<UserRequestTo> user);
    List<UserResponseTo> toUserResponseList(List<User> user);
}
