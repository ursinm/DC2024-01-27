package org.example.publisher.impl.user.mapper;

import org.example.publisher.impl.user.User;
import org.example.publisher.impl.user.dto.UserRequestTo;
import org.example.publisher.impl.user.dto.UserResponseTo;

import java.util.List;

public interface UserMapper {

    UserRequestTo userToRequestTo(User user);

    List<UserRequestTo> userToRequestTo(Iterable<User> user);

    User dtoToEntity(UserRequestTo userRequestTo);

    List<User> dtoToEntity(Iterable<UserRequestTo> userRequestTos);

    UserResponseTo userToResponseTo(User user);

    List<UserResponseTo> userToResponseTo(Iterable<User> user);
    
    
}
