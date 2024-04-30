package org.example.publisher.api.Controllers;

import org.example.publisher.api.exception.DuplicateEntityException;
import org.example.publisher.api.exception.EntityNotFoundException;
import org.example.publisher.impl.user.dto.UserRequestTo;
import org.example.publisher.impl.user.dto.UserResponseTo;
import org.example.publisher.impl.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<UserResponseTo> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserResponseTo getUserById(@PathVariable BigInteger id) throws EntityNotFoundException {
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserResponseTo makeUser(@Valid @RequestBody UserRequestTo userRequestTo) throws DuplicateEntityException {
        return userService.createUser(userRequestTo);
    }

    @PutMapping
    UserResponseTo updateUser(@Valid @RequestBody UserRequestTo userRequestTo) throws EntityNotFoundException{
        return userService.updateUser(userRequestTo);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    void deleteUser(@PathVariable BigInteger id) throws EntityNotFoundException {
        userService.deleteUser(id);
    }
}
