package by.bsuir.rv.controller;

import by.bsuir.rv.dto.StickerRequestTo;
import by.bsuir.rv.dto.StickerResponseTo;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.service.sticker.IStickerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StickerControllerTest {

    @Mock
    private IStickerService stickerService;

    @InjectMocks
    private StickerController stickerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStickers() {
        when(stickerService.getStickers()).thenReturn(Collections.singletonList(new StickerResponseTo()));

        List<StickerResponseTo> result = stickerController.getStickers();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetStickerById() throws EntityNotFoundException {
        BigInteger id = BigInteger.valueOf(1L);
        when(stickerService.getStickerById(id)).thenReturn(new StickerResponseTo());

        StickerResponseTo result = stickerController.getStickerById(id);

        assertNotNull(result);
    }

    @Test
    void testCreateSticker() {
        StickerRequestTo stickerRequest = new StickerRequestTo();
        when(stickerService.createSticker(stickerRequest)).thenReturn(new StickerResponseTo());

        StickerResponseTo result = stickerController.createSticker(stickerRequest);

        assertNotNull(result);
    }

    @Test
    void testUpdateSticker() throws EntityNotFoundException {
        StickerRequestTo stickerRequest = new StickerRequestTo();
        when(stickerService.updateSticker(stickerRequest)).thenReturn(new StickerResponseTo());

        StickerResponseTo result = stickerController.updateSticker(stickerRequest);

        assertNotNull(result);
    }

    @Test
    void testDeleteSticker() throws EntityNotFoundException {
        BigInteger id = BigInteger.valueOf(1L);

        stickerController.deleteSticker(id);

        verify(stickerService, times(1)).deleteSticker(id);
    }
}
