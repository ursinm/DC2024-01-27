package com.example.restapplication.controllers;

import com.example.restapplication.dto.UserRequestTo;
import com.example.restapplication.dto.UserResponseTo;
import com.example.restapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseTo>> getAll(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                       @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                       @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(200).body(userService.getAll(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseTo> getById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<UserResponseTo> save(@RequestBody UserRequestTo user) {
        UserResponseTo userToSave = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userToSave);
    }

    @PutMapping
    public ResponseEntity<UserResponseTo> update(@RequestBody UserRequestTo user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(user));
    }

    @GetMapping("/story/{id}")
    public ResponseEntity<UserResponseTo> getByStoryId(@PathVariable Long id){
        return ResponseEntity.status(200).body(userService.getByStoryId(id));
    }
}
