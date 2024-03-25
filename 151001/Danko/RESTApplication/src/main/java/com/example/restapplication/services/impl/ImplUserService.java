package com.example.restapplication.services.impl;

import com.example.restapplication.dao.UserDAO;
import com.example.restapplication.dto.UserRequestTo;
import com.example.restapplication.dto.UserResponseTo;
import com.example.restapplication.entites.User;
import com.example.restapplication.exceptions.DeleteException;
import com.example.restapplication.exceptions.NotFoundException;
import com.example.restapplication.exceptions.UpdateException;
import com.example.restapplication.mappers.UserListMapper;
import com.example.restapplication.mappers.UserMapper;
import com.example.restapplication.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ImplUserService implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserListMapper userListMapper;
    @Override
    public UserResponseTo getById(Long id) throws NotFoundException {
        Optional<User> user = userDAO.findById(id);
        return user.map(value -> userMapper.toUserResponse(value)).orElseThrow(() -> new NotFoundException("User not found", 40004L));
    }

    @Override
    public List<UserResponseTo> getAll() {
        return userListMapper.toUserResponseList(userDAO.findAll());
    }

    @Override
    public UserResponseTo save(@Valid UserRequestTo requestTo) {
        User userToSave = userMapper.toUser(requestTo);
        return userMapper.toUserResponse(userDAO.save(userToSave));
    }

    @Override
    public void delete(Long id) throws DeleteException {
        userDAO.delete(id);
    }

    @Override
    public UserResponseTo update(@Valid UserRequestTo requestTo) throws UpdateException {
        User userToUpdate = userMapper.toUser(requestTo);
        return userMapper.toUserResponse(userDAO.update(userToUpdate));
    }

    @Override
    public UserResponseTo getByStoryId(Long storyId) throws NotFoundException {
        Optional<User> editor = userDAO.getByStoryId(storyId);
        return editor.map(value -> userMapper.toUserResponse(value)).orElseThrow(() -> new NotFoundException("User not found!", 40004L));
    }
}
