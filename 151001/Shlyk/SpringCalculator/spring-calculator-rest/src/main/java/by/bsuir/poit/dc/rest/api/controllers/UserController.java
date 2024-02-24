package by.bsuir.poit.dc.rest.api.controllers;

import by.bsuir.poit.dc.rest.api.dto.groups.Create;
import by.bsuir.poit.dc.rest.api.dto.groups.Update;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateUserDto;
import by.bsuir.poit.dc.rest.api.dto.response.UserDto;
import by.bsuir.poit.dc.rest.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1.0/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(
	@RequestBody @Validated(Create.class) UpdateUserDto dto
    ) {
	var response = userService.create(dto);
	return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<UserDto> getUsers(
	@RequestParam(value = "news_id", required = false) Long newsId
    ) {
	if (newsId == null) {
	    return userService.getAll();
	}
	return List.of(
	    userService.getUserByNewsId(newsId)
	);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(
	@PathVariable long userId
    ) {
	return userService.getById(userId);
    }

    @PutMapping
    public UserDto updateUser(
	@RequestBody @Validated(Update.class) UpdateUserDto dto
    ) {
	long userId = dto.id();
	return userService.update(userId, dto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(
	@PathVariable long userId
    ) {
	boolean isDeleted = userService.deleteUser(userId);
	HttpStatus status = isDeleted ?
				HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
	return ResponseEntity.status(status).build();
    }
}
