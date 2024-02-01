package com.poluectov.rvlab1.service;

import com.poluectov.rvlab1.dto.user.UserRequestTo;
import com.poluectov.rvlab1.dto.user.UserResponseTo;
import com.poluectov.rvlab1.model.User;
import com.poluectov.rvlab1.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CommonRestService<User, UserRequestTo, UserResponseTo> {

    public UserService(UserRepository userRepository) {
        super(userRepository);
    }
    @Override
    UserResponseTo mapResponseTo(User user) {
        return UserResponseTo.builder()
                .id(user.getId())
                .login(user.getLogin())
                .hashedPassword(user.getPassword()) //TODO: hash
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
