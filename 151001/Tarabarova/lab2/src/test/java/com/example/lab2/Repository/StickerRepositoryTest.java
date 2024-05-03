package com.example.lab2.Repository;

import com.example.lab2.ForEachPostgresqlContainer;
import com.example.lab2.Model.Sticker;
import jakarta.transaction.Transactional;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StickerRepositoryTest {
    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = ForEachPostgresqlContainer.getInstance();

    @Autowired
    private StickerRepository stickerRepository;

    @Test
    public void testFindById(){
        Sticker sticker = new Sticker(0,"1234");
        Sticker res = stickerRepository.save(sticker);
        assertEquals(stickerRepository.findById(res.getId()), Optional.of(res));
    }

    @Test
    public void testSave(){
        Sticker sticker = new Sticker(0,"1234");
        stickerRepository.save(sticker);
        assertNotEquals(sticker.getId(), 0);
    }
    @Test
    public void testDeleteById(){
        Sticker sticker = new Sticker(0,"1234");
        Sticker res = stickerRepository.save(sticker);
        stickerRepository.deleteById(res.getId());
        assertFalse(stickerRepository.existsById(res.getId()));
    }

}