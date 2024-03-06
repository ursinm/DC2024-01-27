package by.bsuir.rv.service.sticker;

import by.bsuir.rv.bean.Sticker;
import by.bsuir.rv.dto.StickerRequestTo;
import by.bsuir.rv.dto.StickerResponseTo;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.repository.exception.RepositoryException;
import by.bsuir.rv.service.sticker.impl.StickerService;
import by.bsuir.rv.util.converter.sticker.StickerConverter;
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

public class StickerServiceTest {

    @Mock
    private StickerConverter stickerConverter;

    @Mock
    private StickerRepositoryMemory stickerRepository;

    @InjectMocks
    private StickerService stickerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStickers_shouldReturnStickerList() {
        Sticker sticker = new Sticker(BigInteger.valueOf(1), "TestSticker", BigInteger.valueOf(1));
        when(stickerRepository.findAll()).thenReturn(Collections.singletonList(sticker));
        when(stickerConverter.convertToResponse(sticker)).thenReturn(new StickerResponseTo());

        List<StickerResponseTo> result = stickerService.getStickers();

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(stickerRepository, times(1)).findAll();
        verify(stickerConverter, times(1)).convertToResponse(sticker);
    }

    @Test
    void getStickerById_shouldReturnStickerById() throws EntityNotFoundException, RepositoryException {
        BigInteger stickerId =  BigInteger.ONE;
        Sticker sticker = new Sticker(stickerId, "TestSticker",  BigInteger.ONE);
        when(stickerRepository.findById(stickerId)).thenReturn(sticker);
        when(stickerConverter.convertToResponse(sticker)).thenReturn(new StickerResponseTo());

        StickerResponseTo result = stickerService.getStickerById(stickerId);

        assertNotNull(result);

        verify(stickerRepository, times(1)).findById(stickerId);
        verify(stickerConverter, times(1)).convertToResponse(sticker);
    }

    @Test
    void createSticker_shouldReturnCreatedSticker() {
        StickerRequestTo requestTo = new StickerRequestTo(BigInteger.ONE, "TestSticker", BigInteger.ONE);
        Sticker sticker = new Sticker(BigInteger.valueOf(1), "TestSticker",  BigInteger.ONE);
        when(stickerConverter.convertToEntity(requestTo)).thenReturn(sticker);
        when(stickerRepository.save(sticker)).thenReturn(sticker);
        when(stickerConverter.convertToResponse(sticker)).thenReturn(new StickerResponseTo());

        StickerResponseTo result = stickerService.createSticker(requestTo);

        assertNotNull(result);

        verify(stickerConverter, times(1)).convertToEntity(requestTo);
        verify(stickerRepository, times(1)).save(sticker);
        verify(stickerConverter, times(1)).convertToResponse(sticker);
    }

    @Test
    void updateSticker_shouldReturnUpdatedSticker() throws EntityNotFoundException, RepositoryException {
        BigInteger stickerId = BigInteger.valueOf(1);
        StickerRequestTo requestTo = new StickerRequestTo(stickerId, "UpdatedSticker",  BigInteger.ONE);
        Sticker sticker = new Sticker(stickerId, "TestSticker",  BigInteger.ONE);
        when(stickerRepository.findById(stickerId)).thenReturn(sticker);
        when(stickerConverter.convertToEntity(requestTo)).thenReturn(sticker);
        when(stickerRepository.save(sticker)).thenReturn(sticker);
        when(stickerConverter.convertToResponse(sticker)).thenReturn(new StickerResponseTo());

        StickerResponseTo result = stickerService.updateSticker(requestTo);

        assertNotNull(result);

        verify(stickerRepository, times(1)).findById(stickerId);
        verify(stickerConverter, times(1)).convertToEntity(requestTo);
        verify(stickerRepository, times(1)).save(sticker);
        verify(stickerConverter, times(1)).convertToResponse(sticker);
    }

    @Test
    void deleteSticker_shouldDeleteSticker() throws RepositoryException {
        BigInteger stickerId = BigInteger.valueOf(1);
        doNothing().when(stickerRepository).deleteById(stickerId);

        assertDoesNotThrow(() -> stickerService.deleteSticker(stickerId));

        verify(stickerRepository, times(1)).deleteById(stickerId);
    }
}
