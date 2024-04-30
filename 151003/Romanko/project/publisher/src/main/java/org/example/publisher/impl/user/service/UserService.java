package org.example.publisher.impl.user.service;

import org.example.publisher.api.exception.DuplicateEntityException;
import org.example.publisher.api.exception.EntityNotFoundException;
import org.example.publisher.impl.user.User;
import org.example.publisher.impl.user.UserRepository;
import org.example.publisher.impl.user.dto.UserRequestTo;
import org.example.publisher.impl.user.dto.UserResponseTo;
import org.example.publisher.impl.user.mapper.Impl.UserMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "userCache")
public class UserService {

    private final UserRepository userRepository;

    private final UserMapperImpl userMapper;
    private final String ENTITY_NAME = "user";


    @Cacheable(cacheNames = "user")
    public List<UserResponseTo> getUsers(){
        List<User> user = userRepository.findAll();
        return userMapper.userToResponseTo(user);
    }
    @Cacheable(cacheNames = "user", key = "#id", unless = "#result == null")
    public UserResponseTo getUserById(BigInteger id) throws EntityNotFoundException{
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return userMapper.userToResponseTo(user.get());
    }

    @CacheEvict(cacheNames = "user", allEntries = true)
    public UserResponseTo createUser(UserRequestTo user) throws DuplicateEntityException {
        try {
            User savedUser = userRepository.save(userMapper.dtoToEntity(user));
            return userMapper.userToResponseTo(savedUser);

        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException(ENTITY_NAME, "login");
        }
    }

    @CacheEvict(cacheNames = "user", allEntries = true)
    public UserResponseTo updateUser(UserRequestTo user) throws EntityNotFoundException {
        if (userRepository.findById(user.getId()).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, user.getId());
        }
        User savedUser = userRepository.save(userMapper.dtoToEntity(user));
        return userMapper.userToResponseTo(savedUser);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "user", key = "#id"),
            @CacheEvict(cacheNames = "user", allEntries = true) })
    public void deleteUser(BigInteger id) throws EntityNotFoundException {
        if (userRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        userRepository.deleteById(id);
    }
}
