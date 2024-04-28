package by.bsuir.romankokarev.impl.service;

import by.bsuir.romankokarev.api.MessageMapper;
import by.bsuir.romankokarev.api.Service;
import by.bsuir.romankokarev.impl.bean.User;
import by.bsuir.romankokarev.impl.dto.UserRequestTo;
import by.bsuir.romankokarev.impl.dto.UserResponseTo;
import by.bsuir.romankokarev.api.UserMapper;
import by.bsuir.romankokarev.impl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserService implements Service<UserResponseTo, UserRequestTo> {

    @Autowired
    private UserRepository userRepository;

    public UserService() {

    }

    public List<UserResponseTo> getAll() {
        List<User> userList = userRepository.getAll();
        List<UserResponseTo> resultList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            resultList.add(UserMapper.INSTANCE.UserToUserResponseTo(userList.get(i)));
        }
        return resultList;
    }

    public UserResponseTo update(UserRequestTo updatingUser) {
        User user = UserMapper.INSTANCE.UserRequestToToUser(updatingUser);
        if (validateUser(user)) {
            boolean result = userRepository.update(user);
            UserResponseTo responseTo = result ? UserMapper.INSTANCE.UserToUserResponseTo(user) : null;
            return responseTo;
        }
        return new UserResponseTo();
    }

    public UserResponseTo get(long id) {
        return UserMapper.INSTANCE.UserToUserResponseTo(userRepository.get(id));
    }

    public UserResponseTo delete(long id) {
        return UserMapper.INSTANCE.UserToUserResponseTo(userRepository.delete(id));
    }

    public UserResponseTo add(UserRequestTo userRequestTo) {
        User user = UserMapper.INSTANCE.UserRequestToToUser(userRequestTo);
        return UserMapper.INSTANCE.UserToUserResponseTo(userRepository.insert(user));
    }

    private boolean validateUser(User user) {
        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        String login = user.getLogin();
        String password = user.getPassword();
        return (firstname.length() >= 2 && firstname.length() <= 64) && (lastname.length() >= 2 && lastname.length() <= 64) && (password.length() >= 8 && firstname.length() <= 128) && (login.length() >= 2 && login.length() <= 64);
    }
}
