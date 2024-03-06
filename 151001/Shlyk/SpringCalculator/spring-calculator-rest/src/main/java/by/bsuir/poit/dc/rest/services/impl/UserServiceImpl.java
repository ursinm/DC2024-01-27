package by.bsuir.poit.dc.rest.services.impl;

import by.bsuir.poit.dc.context.CatchLevel;
import by.bsuir.poit.dc.context.CatchThrows;
import by.bsuir.poit.dc.rest.api.dto.mappers.UserMapper;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateUserDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.dto.response.UserDto;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.rest.dao.UserRepository;
import by.bsuir.poit.dc.rest.model.User;
import by.bsuir.poit.dc.rest.services.UserService;
import com.google.errorprone.annotations.Keep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */

@Slf4j
@Service
@CatchLevel(DataAccessException.class)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @CatchThrows(
	call = "newUserAlreadyExistsException"
    )
    public UserDto create(UpdateUserDto dto) {
	//todo: userMapper should accept also hashing strategy to construct User entity
	User entity = userMapper.toEntity(dto);
	User savedEntity = userRepository.save(entity);
	return userMapper.toDto(savedEntity);
    }

    @Override
    public UserDto getById(long userId) {
	return userRepository
		   .findById(userId)
		   .map(userMapper::toDto)
		   .orElseThrow(() -> newUserNotFoundException(userId));
    }

    @Override
    public UserDto getUserByNewsId(long newsId) {
	return userRepository
		   .findByNewsId(newsId)
		   .map(userMapper::toDto)
		   .orElseThrow(() -> newUserNotFoundByNewsException(newsId));
    }

    @Override
    public List<UserDto> getAll() {
	return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    @Transactional
    @CatchThrows(
	call = "newUserModifyingException",
	args = "userId")
    public UserDto update(long userId, UpdateUserDto dto) {
	User user = userRepository
			.findById(userId)
			.orElseThrow(() -> newUserNotFoundException(userId));
	User updatedUser = userMapper.partialUpdate(user, dto);
	User savedUser = userRepository.save(updatedUser);
	return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    public PresenceDto deleteUser(long userId) {
	return PresenceDto
		   .wrap(userRepository.existsById(userId))
		   .ifPresent(() -> userRepository.deleteById(userId));
    }

    @Keep
    public ResourceModifyingException newUserModifyingException(long userId, Throwable e) {
	final String frontMsg = STR."Failed to modify user by id=\{userId}";
	final String msg = STR."\{frontMsg} \{e.getMessage()}";
	log.warn(msg);
	return new ResourceModifyingException(frontMsg, 51);
    }

    @Keep
    public ResourceModifyingException newUserAlreadyExistsException(Throwable e) {
	final String msg = STR."Failed to create new user. Actual cause =\{e.getMessage()}";
	log.warn(msg);
	return new ResourceModifyingException(msg, 32);

    }

    private static ResourceNotFoundException newUserNotFoundByNewsException(long newsId) {
	final String msg = STR."Failed to find any user by news id = \{newsId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 48);

    }

    private static ResourceNotFoundException newUserNotFoundException(long userId) {
	final String msg = STR."Failed to find user by id=\{userId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 42);

    }
}
