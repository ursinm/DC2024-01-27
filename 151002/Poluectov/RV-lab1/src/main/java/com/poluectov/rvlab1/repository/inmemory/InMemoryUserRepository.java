package com.poluectov.rvlab1.repository.inmemory;

import com.poluectov.rvlab1.dto.user.UserRequestTo;
import com.poluectov.rvlab1.model.User;
import com.poluectov.rvlab1.repository.UserRepository;
import com.poluectov.rvlab1.utils.dtoconverter.DtoConverter;
import org.springframework.stereotype.Component;

@Component
public class InMemoryUserRepository extends InMemoryRepository<User, UserRequestTo> implements UserRepository {

    public InMemoryUserRepository(DtoConverter<UserRequestTo, User> convert) {
        super(convert);
    }
}
