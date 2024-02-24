package by.bsuir.rv.repository.sticker;

import by.bsuir.rv.bean.Sticker;
import by.bsuir.rv.repository.exception.RepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StickerRepositoryMemoryTest {

    @InjectMocks
    private StickerRepositoryMemory stickerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_shouldSaveSticker() {
        Sticker sticker = new Sticker();

        Sticker savedSticker = stickerRepository.save(sticker);

        assertNotNull(savedSticker.getId());
        assertEquals(1, stickerRepository.findAll().size());
    }

    @Test
    void testFindAll_shouldReturnAllStickers() {
        Sticker sticker1 = new Sticker();
        Sticker sticker2 = new Sticker();
        stickerRepository.save(sticker1);
        stickerRepository.save(sticker2);

        List<Sticker> stickers = stickerRepository.findAll();

        assertEquals(2, stickers.size());
    }

    @Test
    void testFindById_shouldReturnStickerById() throws RepositoryException {
        Sticker sticker = new Sticker();
        Sticker savedSticker = stickerRepository.save(sticker);

        Sticker foundSticker = stickerRepository.findById(savedSticker.getId());

        assertNotNull(foundSticker);
        assertEquals(savedSticker.getId(), foundSticker.getId());
    }

    @Test
    void testFindById_shouldThrowExceptionIfStickerNotFound() {
        BigInteger nonExistingId = BigInteger.valueOf(999);

        assertThrows(RepositoryException.class, () -> stickerRepository.findById(nonExistingId));
    }

    @Test
    void testFindAllById_shouldReturnStickersByIds() throws RepositoryException {
        Sticker sticker1 = new Sticker();
        Sticker sticker2 = new Sticker();
        Sticker savedSticker1 = stickerRepository.save(sticker1);
        Sticker savedSticker2 = stickerRepository.save(sticker2);

        List<BigInteger> idsToFind = Arrays.asList(savedSticker1.getId(), savedSticker2.getId());

        List<Sticker> foundStickers = stickerRepository.findAllById(idsToFind);

        assertEquals(2, foundStickers.size());
    }

    @Test
    void testFindAllById_shouldThrowExceptionIfStickersNotFound() {
        BigInteger nonExistingId1 = BigInteger.valueOf(999);
        BigInteger nonExistingId2 = BigInteger.valueOf(1000);
        List<BigInteger> nonExistingIds = Arrays.asList(nonExistingId1, nonExistingId2);

        assertThrows(RepositoryException.class, () -> stickerRepository.findAllById(nonExistingIds));
    }

    @Test
    void testDeleteById_shouldDeleteStickerById() throws RepositoryException {
        Sticker sticker = new Sticker();
        Sticker savedSticker = stickerRepository.save(sticker);

        stickerRepository.deleteById(savedSticker.getId());

        assertEquals(0, stickerRepository.findAll().size());
    }

    @Test
    void testDeleteById_shouldThrowExceptionIfStickerNotFound() {
        BigInteger nonExistingId = BigInteger.valueOf(999);

        assertThrows(RepositoryException.class, () -> stickerRepository.deleteById(nonExistingId));
    }
}
