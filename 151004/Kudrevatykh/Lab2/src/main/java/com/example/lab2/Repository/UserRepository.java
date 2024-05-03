package com.example.lab2.Repository;

import com.example.lab2.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

    Page<User> findAll (Pageable pageable);
    boolean existsByLogin(String login);
}
