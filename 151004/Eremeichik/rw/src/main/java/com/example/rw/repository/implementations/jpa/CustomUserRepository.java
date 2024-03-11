package com.example.rw.repository.implementations.jpa;

import com.example.rw.model.entity.implementations.User;
import com.example.rw.repository.interfaces.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface CustomUserRepository extends JpaRepository<User,Long>, UserRepository {
}
