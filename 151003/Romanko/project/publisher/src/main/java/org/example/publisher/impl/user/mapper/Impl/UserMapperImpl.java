package org.example.publisher.impl.user.mapper.Impl;

import org.example.publisher.impl.user.User;
import org.example.publisher.impl.user.dto.UserRequestTo;
import org.example.publisher.impl.user.dto.UserResponseTo;
import org.example.publisher.impl.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserMapperImpl implements UserMapper {
    @Override
    public UserRequestTo userToRequestTo(User user) {
        return new UserRequestTo(user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname());
    }

    @Override
    public List<UserRequestTo> userToRequestTo(Iterable<User> user) {
        return StreamSupport.stream(user.spliterator(), false)
                .map(this::userToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public User dtoToEntity(UserRequestTo userRequestTo) {
        return new User(userRequestTo.getId(),
                userRequestTo.getLogin(),
                userRequestTo.getPassword(),
                userRequestTo.getFirstname(),
                userRequestTo.getLastname());
    }

    @Override
    public List<User> dtoToEntity(Iterable<UserRequestTo> userRequestTos) {
        return StreamSupport.stream(userRequestTos.spliterator(), false)
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseTo userToResponseTo(User user) {
        return new UserResponseTo(user.getId(),
                user.getLogin(),
                user.getFirstname(),
                user.getLastname());
    }

    @Override
    public List<UserResponseTo> userToResponseTo(Iterable<User> user) {
        return StreamSupport.stream(user.spliterator(), false)
                .map(this::userToResponseTo)
                .collect(Collectors.toList());
    }
}
