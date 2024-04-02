package com.example.rw.repository.implementations.in_memory;

import com.example.rw.model.entity.implementations.User;
import com.example.rw.repository.interfaces.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserInMemoryRepository extends InMemoryRepositoryWithLongId<User> implements UserRepository {
}
