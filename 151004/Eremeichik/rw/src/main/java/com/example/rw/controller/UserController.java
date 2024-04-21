package com.example.rw.controller;

import com.example.rw.exception.model.not_found.EntityNotFoundException;
import com.example.rw.model.dto.user.UserRequestTo;
import com.example.rw.model.dto.user.UserResponseTo;
import com.example.rw.model.entity.implementations.User;
import com.example.rw.repository.implementations.jpa.CustomUserRepository;
import com.example.rw.service.db_operations.interfaces.UserService;
import com.example.rw.service.dto_converter.interfaces.UserToConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.request.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserToConverter userToConverter;

    @PostMapping
    public ResponseEntity<UserResponseTo> createUser(@RequestBody @Valid UserRequestTo userRequestTo) {
        User user = userToConverter.convertToEntity(userRequestTo);
        userService.save(user);
        UserResponseTo userResponseTo = userToConverter.convertToDto(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponseTo);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseTo>> receiveAllUsers() {
        List<User> users = userService.findAll();
        List<UserResponseTo> responseList = users.stream().map(userToConverter::convertToDto).toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseTo> receiveById(@PathVariable Long id) {
        UserResponseTo userResponseTo = userToConverter.convertToDto(userService.findById(id));
        return ResponseEntity.ok(userResponseTo);
    }

    @PutMapping()
    public ResponseEntity<UserResponseTo> updateUser(@RequestBody @Valid UserRequestTo userRequestTo) {
        User user = userToConverter.convertToEntity(userRequestTo);
        userService.update(user);
        UserResponseTo userResponseTo = userToConverter.convertToDto(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponseTo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            userService.deleteById(id);
        } catch (EntityNotFoundException entityNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
