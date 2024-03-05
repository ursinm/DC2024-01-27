package by.bsuir.poit.dc.rest.services;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateUserDto;
import by.bsuir.poit.dc.rest.api.dto.response.DeleteDto;
import by.bsuir.poit.dc.rest.api.dto.response.UserDto;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
public interface UserService {
    UserDto create(@Valid UpdateUserDto dto);

    UserDto getById(long userId);

    UserDto getUserByNewsId(long newsId);

    @Deprecated
    List<UserDto> getAll();

    UserDto update(long userId, @Valid UpdateUserDto dto);

    /*
    return true if user was deleted
     */
    DeleteDto deleteUser(long userId);
}
