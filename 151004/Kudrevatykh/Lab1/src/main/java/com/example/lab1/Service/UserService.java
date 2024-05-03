package com.example.lab1.Service;

import com.example.lab1.DAO.UserDao;
import com.example.lab1.DTO.UserRequestTo;
import com.example.lab1.DTO.UserResponseTo;
import com.example.lab1.Exception.NotFoundException;
import com.example.lab1.Mapper.UserListMapper;
import com.example.lab1.Mapper.UserMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserDao userDao;
    @Autowired
    UserListMapper userListMapper;

    public UserResponseTo create(@Valid UserRequestTo user){
        return userMapper.userToUserResponse(userDao.create(userMapper.userRequestToUser(user)));
    }
    public List<UserResponseTo> readAll(){
        return userListMapper.toUserResponseList(userDao.readAll());
    }
    public UserResponseTo read(@Min(0) int id) throws NotFoundException {
        UserResponseTo cr = userMapper.userToUserResponse(userDao.read(id));
        if(cr != null)
            return cr;
        else
            throw new NotFoundException("User not found", 404);
    }
    public UserResponseTo update(@Valid UserRequestTo user, @Min(0) int id){
        UserResponseTo cr = userMapper.userToUserResponse(userDao.update(userMapper.userRequestToUser(user),id));
        if(cr != null)
            return cr;
        else
            throw new NotFoundException("User not found", 404);
    }
    public boolean delete(@Min(0) int id){
        if(userDao.delete(id))
            return true;
        else
            throw new NotFoundException("User not found", 404);
    }


}
