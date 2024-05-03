package by.bsuir.controllers;

import by.bsuir.dto.UserRequestTo;
import by.bsuir.dto.UserResponseTo;
import by.bsuir.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/users")
public class UserController {

    @Autowired
    UserService UserService;

    private static final int SUCCESS_CODE = 200;

    @GetMapping
    public ResponseEntity<List<UserResponseTo>> getUsers(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(200).body(UserService.getUsers(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseTo> getUser(@PathVariable Long id) {
        return ResponseEntity.status(SUCCESS_CODE).body(UserService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseTo> saveUser(@RequestBody UserRequestTo User) {
        UserResponseTo savedUser = UserService.saveUser(User);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping
    public ResponseEntity<UserResponseTo> updateUser(@RequestBody UserRequestTo User) {
        return ResponseEntity.status(HttpStatus.OK).body(UserService.updateUser(User));
    }

    @GetMapping("/byIssue/{id}")
    public ResponseEntity<UserResponseTo> getUserByIssueId(@PathVariable Long id){
        return ResponseEntity.status(SUCCESS_CODE).body(UserService.getUserByIssueId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        UserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
