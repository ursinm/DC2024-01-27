package com.poluectov.rvlab1.repository;

import com.poluectov.rvlab1.dto.user.UserRequestTo;
import com.poluectov.rvlab1.model.User;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends ICommonRepository<User, UserRequestTo> {
}
