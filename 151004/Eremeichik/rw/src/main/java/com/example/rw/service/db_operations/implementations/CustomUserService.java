package com.example.rw.service.db_operations.implementations;

import com.example.rw.exception.model.not_found.EntityNotFoundException;
import com.example.rw.model.entity.implementations.News;
import com.example.rw.model.entity.implementations.User;
import com.example.rw.repository.interfaces.UserRepository;
import com.example.rw.service.db_operations.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(Long id) throws EntityNotFoundException {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User entity) {
        userRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        boolean wasDeleted = userRepository.findById(id).isPresent();
        if(!wasDeleted){
            throw new EntityNotFoundException(id);
        } else{
            userRepository.deleteById(id);
        }
    }

    @Override
    public void update(User entity) {
        boolean wasUpdated = userRepository.findById(entity.getId()).isPresent();
        if(!wasUpdated){
            throw new EntityNotFoundException();
        } else{
            userRepository.save(entity);
        }
    }
}
