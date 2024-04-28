package by.bsuir.vladislavmatsiushenko.api;

import by.bsuir.vladislavmatsiushenko.impl.bean.User;
import by.bsuir.vladislavmatsiushenko.impl.dto.UserRequestTo;
import by.bsuir.vladislavmatsiushenko.impl.dto.UserResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserResponseTo UserToUserResponseTo(User user);
    UserRequestTo UserToUserRequestTo(User user);
    User UserResponseToToUser(UserResponseTo userResponseTo);
    User UserRequestToToUser(UserRequestTo userRequestTo);
}
