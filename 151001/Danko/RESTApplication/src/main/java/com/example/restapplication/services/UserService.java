package com.example.restapplication.services;

import com.example.restapplication.dto.UserRequestTo;
import com.example.restapplication.dto.UserResponseTo;
import com.example.restapplication.exceptions.NotFoundException;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends CrudService<UserRequestTo, UserResponseTo> {
    UserResponseTo getByStoryId(@Min(0) Long storyId) throws NotFoundException;
}
