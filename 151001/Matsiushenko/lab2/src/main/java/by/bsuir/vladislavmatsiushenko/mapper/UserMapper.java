package by.bsuir.vladislavmatsiushenko.mapper;

import by.bsuir.vladislavmatsiushenko.dto.UserRequestTo;
import by.bsuir.vladislavmatsiushenko.dto.UserResponseTo;
import by.bsuir.vladislavmatsiushenko.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userRequestToUser(UserRequestTo userRequestTo);

    UserResponseTo userToUserResponse(User user);
}
