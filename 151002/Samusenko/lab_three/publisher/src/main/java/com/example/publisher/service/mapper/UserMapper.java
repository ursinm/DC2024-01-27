package com.example.publisher.service.mapper;

import com.example.publisher.model.entity.User;
import com.example.publisher.model.request.UserRequestTo;
import com.example.publisher.model.response.UserResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    UserResponseTo getResponse(User user);

    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    List<UserResponseTo> getListResponse(Iterable<User> users);

    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    User getUser(UserRequestTo userRequestTo);
}
