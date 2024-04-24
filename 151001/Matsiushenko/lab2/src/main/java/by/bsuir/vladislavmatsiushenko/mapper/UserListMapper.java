package by.bsuir.vladislavmatsiushenko.mapper;

import by.bsuir.vladislavmatsiushenko.dto.UserRequestTo;
import by.bsuir.vladislavmatsiushenko.dto.UserResponseTo;
import by.bsuir.vladislavmatsiushenko.model.User;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserListMapper {
    List<User> toUserList(List<UserRequestTo> users);

    List<UserResponseTo> toUserResponseList(List<User> users);
}
