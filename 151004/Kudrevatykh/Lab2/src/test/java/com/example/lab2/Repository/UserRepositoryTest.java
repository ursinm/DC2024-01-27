package com.example.lab2.Repository;

import com.example.lab2.ForEachPostgresqlContainer;
import com.example.lab2.Model.User;
import jakarta.transaction.Transactional;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = ForEachPostgresqlContainer.getInstance();
    @Autowired
    private UserRepository userRepository;
    @Test
    public void testFindById(){
        User creator = new User(0,"1010", "1010101010", "vika", "loziuk");
        User res = userRepository.save(creator);
        assertEquals(userRepository.findById(res.getId()), Optional.of(res));
    }
    @Test
    public void testSave(){
        User creator = new User(0,"10101", "1010101010", "vika", "loziuk");
        userRepository.save(creator);
        assertNotEquals(creator.getId(),0);
    }
    @Test
    public void testDeleteById(){
        User creator = new User(0,"10103", "1010101010", "vika", "loziuk");
        User res = userRepository.save(creator);
        userRepository.deleteById(res.getId());
        assertFalse(userRepository.existsById(res.getId()));
    }
}