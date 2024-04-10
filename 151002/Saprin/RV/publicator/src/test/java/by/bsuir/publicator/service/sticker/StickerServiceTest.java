package by.bsuir.publicator.service.sticker;

import by.bsuir.publicator.bean.Issue;
import by.bsuir.publicator.bean.Sticker;
import by.bsuir.publicator.dto.StickerRequestTo;
import by.bsuir.publicator.dto.StickerResponseTo;
import by.bsuir.publicator.exception.EntityNotFoundException;
import by.bsuir.publicator.repository.issue.IssueRepository;
import by.bsuir.publicator.repository.sticker.StickerRepository;
import by.bsuir.publicator.service.sticker.impl.StickerService;
import by.bsuir.publicator.util.converter.sticker.StickerConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StickerServiceTest {

    @Mock
    private StickerConverter stickerConverter;

    @Mock
    private StickerRepository stickerRepository;

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private StickerService stickerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStickers_shouldReturnStickerList() {
        Sticker sticker = new Sticker(BigInteger.valueOf(1), "TestSticker", new ArrayList<>());
        when(stickerRepository.findAll()).thenReturn(Collections.singletonList(sticker));
        when(stickerConverter.convertToResponse(sticker)).thenReturn(new StickerResponseTo());

        List<StickerResponseTo> result = stickerService.getStickers();

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(stickerRepository, times(1)).findAll();
        verify(stickerConverter, times(1)).convertToResponse(sticker);
    }

    @Test
    void getStickerById_shouldReturnStickerById() throws EntityNotFoundException {
        BigInteger stickerId =  BigInteger.ONE;
        Sticker sticker = new Sticker(stickerId, "TestSticker", new ArrayList<>());
        when(stickerRepository.findById(stickerId)).thenReturn(Optional.of(sticker));
        when(stickerConverter.convertToResponse(sticker)).thenReturn(new StickerResponseTo());

        StickerResponseTo result = stickerService.getStickerById(stickerId);

        assertNotNull(result);

        verify(stickerRepository, times(1)).findById(stickerId);
        verify(stickerConverter, times(1)).convertToResponse(sticker);
    }

    @Test
    void createSticker_shouldReturnCreatedSticker() {
        StickerRequestTo requestTo = new StickerRequestTo(BigInteger.ONE, "TestSticker", new ArrayList<>());
        Sticker sticker = new Sticker(BigInteger.valueOf(1), "TestSticker",  new ArrayList<>());
        when(stickerConverter.convertToEntity(requestTo, new ArrayList<>())).thenReturn(sticker);
        when(stickerRepository.save(sticker)).thenReturn(sticker);
        when(stickerConverter.convertToResponse(sticker)).thenReturn(new StickerResponseTo());
        when(issueRepository.findAllById(sticker.getIssues().stream().map(Issue::getIss_id).toList())).thenReturn(new ArrayList<>());

        StickerResponseTo result = stickerService.createSticker(requestTo);

        assertNotNull(result);

        verify(stickerConverter, times(1)).convertToEntity(requestTo, new ArrayList<>());
        verify(stickerRepository, times(1)).save(sticker);
        verify(stickerConverter, times(1)).convertToResponse(sticker);
    }

    @Test
    void updateSticker_shouldReturnUpdatedSticker() throws EntityNotFoundException {
        BigInteger stickerId = BigInteger.valueOf(1);
        StickerRequestTo requestTo = new StickerRequestTo(stickerId, "UpdatedSticker", new ArrayList<>());
        Sticker sticker = new Sticker(stickerId, "TestSticker", new ArrayList<>());
        when(stickerRepository.findById(stickerId)).thenReturn(Optional.of(sticker));
        when(stickerConverter.convertToEntity(requestTo, new ArrayList<>())).thenReturn(sticker);
        when(stickerRepository.save(sticker)).thenReturn(sticker);
        when(stickerConverter.convertToResponse(sticker)).thenReturn(new StickerResponseTo());
        when(issueRepository.findAllById(Collections.singletonList(stickerId))).thenReturn(new ArrayList<>());

        StickerResponseTo result = stickerService.updateSticker(requestTo);

        assertNotNull(result);

        verify(stickerRepository, times(1)).findById(stickerId);
        verify(stickerConverter, times(1)).convertToEntity(requestTo, new ArrayList<>());
        verify(stickerRepository, times(1)).save(sticker);
        verify(stickerConverter, times(1)).convertToResponse(sticker);
    }

    @Test
    void deleteSticker_shouldDeleteSticker() {
        BigInteger stickerId = BigInteger.valueOf(1);

        when(stickerRepository.findById(stickerId)).thenReturn(Optional.of(new Sticker()));

        doNothing().when(stickerRepository).deleteById(stickerId);

        assertDoesNotThrow(() -> stickerService.deleteSticker(stickerId));

        verify(stickerRepository, times(1)).deleteById(stickerId);
    }
}
