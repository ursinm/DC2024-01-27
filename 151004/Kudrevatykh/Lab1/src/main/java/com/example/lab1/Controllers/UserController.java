package com.example.lab1.Controllers;

import com.example.lab1.DTO.UserRequestTo;
import com.example.lab1.DTO.UserResponseTo;
import com.example.lab1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "users")
    public ResponseEntity<?> create(@RequestBody UserRequestTo user) {
        UserResponseTo cr = userService.create(user);
        return new ResponseEntity<>(cr, HttpStatus.CREATED);
    }

    @GetMapping(value = "users", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<UserResponseTo>> read() {
        final List<UserResponseTo> users = userService.readAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<UserResponseTo> read(@PathVariable(name = "id") int id) {
        final UserResponseTo user = userService.read(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "users")
    public ResponseEntity<?> update(@RequestBody UserRequestTo user) {
        final UserResponseTo updated = userService.update(user, user.getId());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping(value = "users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
