package by.bsuir.services;

import by.bsuir.dto.UserRequestTo;
import by.bsuir.dto.UserResponseTo;
import by.bsuir.entities.User;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.DuplicationException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.UserListMapper;
import by.bsuir.mapper.UserMapper;
import by.bsuir.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserService {

    @Autowired
    UserMapper UserMapper;
    @Autowired
    UserRepository userDao;
    @Autowired
    UserListMapper UserListMapper;

    public UserResponseTo getUserById(@Min(0) Long id) throws NotFoundException{
        Optional<User> User = userDao.findById(id);
        return User.map(value -> UserMapper.UserToUserResponse(value)).orElseThrow(() -> new NotFoundException("User not found!", 40004L));
    }

    public List<UserResponseTo> getUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder!=null && sortOrder.equals("asc")){
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<User> users = userDao.findAll(pageable);
        return UserListMapper.toUserResponseList(users.toList());
    }

    public UserResponseTo saveUser(@Valid UserRequestTo user) throws DuplicationException {
        User userToSafe = UserMapper.UserRequestToUser(user);
        if (userDao.existsByLogin(userToSafe.getLogin())){
            throw new DuplicationException("Login duplication", 40005L);
        }
        return UserMapper.UserToUserResponse(userDao.save(userToSafe));
    }

    public UserResponseTo updateUser(@Valid UserRequestTo User) throws UpdateException {
        User userToUpdate = UserMapper.UserRequestToUser(User);
        if (!userDao.existsById(userToUpdate.getId())){
            throw new UpdateException("User not found!", 40004L);
        } else {
            return UserMapper.UserToUserResponse(userDao.save(userToUpdate));
        }
    }

    public UserResponseTo getUserByIssueId(@Min(0) Long issueId) throws NotFoundException {
        User user = userDao.findUserByIssueId(issueId);
        return UserMapper.UserToUserResponse(user);
    }

    public void deleteUser(@Min(0) Long id) throws DeleteException {
        if (!userDao.existsById(id)) {
            throw new DeleteException("User not found!", 40004L);
        } else {
            userDao.deleteById(id);
        }
    }
}
