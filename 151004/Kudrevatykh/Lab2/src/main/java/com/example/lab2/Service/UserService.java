package com.example.lab2.Service;

import com.example.lab2.DTO.UserRequestTo;
import com.example.lab2.DTO.UserResponseTo;
import com.example.lab2.Exception.DuplicateException;
import com.example.lab2.Exception.NotFoundException;
import com.example.lab2.Mapper.UserListMapper;
import com.example.lab2.Mapper.UserMapper;
import com.example.lab2.Model.User;
import com.example.lab2.Repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@Validated
public class UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserListMapper userListMapper;

    public UserResponseTo create(@Valid UserRequestTo userRequestTo){
        User user = userMapper.userRequestToUser(userRequestTo);
        if(userRepository.existsByLogin(user.getLogin())){
            throw new DuplicateException("Login duplication", 403);
        }
        return userMapper.userToUserResponse(userRepository.save(user));
    }
    public List<UserResponseTo> readAll(int pageInd, int numOfElem, String sortedBy, String direction){
        Pageable p;
        if(direction != null && direction.equals("asc")){
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).ascending());
        }
        else{
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).descending());
        }
        Page<User> res = userRepository.findAll(p);
        return userListMapper.toUserResponseList(res.toList());
    }

    public UserResponseTo read(@Min(0) int id) throws NotFoundException {
        if(userRepository.existsById(id)){
            UserResponseTo us = userMapper.userToUserResponse(userRepository.getReferenceById(id));
            return us;
        }
        else
            throw new NotFoundException("User not found", 404);
    }
    public UserResponseTo update(@Valid UserRequestTo user, @Min(0) int id){
        if(userRepository.existsById(id)){
            User cr =  userMapper.userRequestToUser(user);
            cr.setId(id);
            return userMapper.userToUserResponse(userRepository.save(cr));
        }
        else
            throw new NotFoundException("User not found", 404);
    }
    public boolean delete(@Min(0) int id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        else
            throw new NotFoundException("User not found", 404);
    }


}
