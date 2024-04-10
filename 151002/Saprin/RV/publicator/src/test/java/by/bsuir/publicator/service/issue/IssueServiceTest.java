package by.bsuir.publicator.service.issue;

import by.bsuir.publicator.bean.Editor;
import by.bsuir.publicator.bean.Issue;
import by.bsuir.publicator.bean.Sticker;
import by.bsuir.publicator.dto.IssueRequestTo;
import by.bsuir.publicator.dto.IssueResponseTo;
import by.bsuir.publicator.exception.DuplicateEntityException;
import by.bsuir.publicator.exception.EntityNotFoundException;
import by.bsuir.publicator.repository.editor.EditorRepository;
import by.bsuir.publicator.repository.issue.IssueRepository;
import by.bsuir.publicator.repository.sticker.StickerRepository;
import by.bsuir.publicator.service.issue.impl.IssueService;
import by.bsuir.publicator.util.converter.issue.IssueConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private EditorRepository editorRepository;

    @Mock
    private StickerRepository stickerRepository;

    @Mock
    private IssueConverter issueConverter;

    @InjectMocks
    private IssueService issueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getIssues_shouldReturnListOfIssues() {
        BigInteger editorId = BigInteger.valueOf(1);
        BigInteger stickerId = BigInteger.valueOf(2);
        BigInteger issueId = BigInteger.valueOf(3);

        Editor editor = new Editor(editorId, "TestEditor", "Password", "First", "Last");
        Issue issue = new Issue(issueId, new Editor(), "TestTitle", "TestText", new Date(), new Date());
        Sticker sticker = new Sticker(stickerId, "TestSticker", new ArrayList<>());

        when(issueRepository.findAll()).thenReturn(List.of(issue));
        when(editorRepository.findAllById(Collections.singletonList(editorId))).thenReturn(Collections.singletonList(editor));
        when(stickerRepository.findAllById(Collections.singletonList(stickerId))).thenReturn(Collections.singletonList(sticker));
        when(issueConverter.convertToResponse(issue)).thenReturn(new IssueResponseTo());

        List<IssueResponseTo> result = issueService.getIssues();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(issueRepository, times(1)).findAll();
        verify(issueConverter, times(1)).convertToResponse(issue);
    }

    @Test
    void getIssueById_shouldReturnIssueById() throws EntityNotFoundException {
        BigInteger editorId = BigInteger.valueOf(1);
        BigInteger stickerId = BigInteger.valueOf(2);
        BigInteger issueId = BigInteger.valueOf(3);

        Editor editor = new Editor(editorId, "TestEditor", "Password", "First", "Last");
        Issue issue = new Issue(issueId, new Editor(), "TestTitle", "TestText", new Date(), new Date());
        Sticker sticker = new Sticker(stickerId, "TestSticker", new ArrayList<>());

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));
        when(editorRepository.findById(editorId)).thenReturn(Optional.of(editor));
        when(stickerRepository.findAllById(Collections.singletonList(stickerId))).thenReturn(Collections.singletonList(sticker));
        when(issueConverter.convertToResponse(issue)).thenReturn(new IssueResponseTo());

        IssueResponseTo result = issueService.getIssueById(issueId);

        assertNotNull(result);
        verify(issueRepository, times(1)).findById(issueId);
        verify(issueConverter, times(1)).convertToResponse(issue);
    }

    @Test
    void addIssue_shouldAddIssueAndReturnResponse() throws EntityNotFoundException, DuplicateEntityException {
        BigInteger editorId = BigInteger.valueOf(1);
        BigInteger stickerId = BigInteger.valueOf(2);
        BigInteger issueId = BigInteger.valueOf(3);

        Editor editor = new Editor(editorId, "TestEditor", "Password", "First", "Last");
        IssueRequestTo issueRequestTo = new IssueRequestTo(issueId, editor.getEd_id(), "TestTitle", "TestText", new Date(), new Date());
        Issue issueEntity = new Issue(issueId, new Editor(), "TestTitle", "TestText", new Date(), new Date());
        IssueResponseTo expectedResponse = new IssueResponseTo(issueId, editor.getEd_id(), "TestTitle", "TestText", new Date(), new Date());

        Sticker sticker = new Sticker(stickerId, "TestSticker", new ArrayList<>());


        when(issueConverter.convertToEntity(issueRequestTo, editor)).thenReturn(issueEntity);
        when(issueRepository.save(issueEntity)).thenReturn(issueEntity);
        when(issueConverter.convertToResponse(issueEntity)).thenReturn(expectedResponse);
        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issueEntity));
        when(editorRepository.findById(editorId)).thenReturn(Optional.of(editor));
        when(stickerRepository.findAllById(Collections.singletonList(stickerId))).thenReturn(Collections.singletonList(sticker));
        when(issueConverter.convertToResponse(issueEntity)).thenReturn(expectedResponse);

        IssueResponseTo result = issueService.addIssue(issueRequestTo);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(issueConverter, times(1)).convertToEntity(issueRequestTo, editor);
        verify(issueRepository, times(1)).save(issueEntity);
        verify(issueConverter, times(1)).convertToResponse(issueEntity);
    }


    @Test
    void deleteIssue_shouldDeleteIssueById() {
        BigInteger issueId = BigInteger.valueOf(1);

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(new Issue()));

        doNothing().when(issueRepository).deleteById(issueId);

        assertDoesNotThrow(() -> issueService.deleteIssue(issueId));

        verify(issueRepository, times(1)).deleteById(issueId);
    }

    @Test
    void updateIssue_shouldUpdateIssueAndReturnResponse() throws EntityNotFoundException, DuplicateEntityException {
        BigInteger editorId = BigInteger.valueOf(1);
        BigInteger stickerId = BigInteger.valueOf(2);
        BigInteger issueId = BigInteger.valueOf(3);

        Editor editor = new Editor(editorId, "TestEditor", "Password", "First", "Last");

        IssueRequestTo issueRequestTo = new IssueRequestTo(issueId, editor.getEd_id(), "TestTitle", "TestText", new Date(), new Date());
        Issue issueEntity = new Issue(issueId, new Editor(), "TestTitle", "TestText", new Date(), new Date());
        IssueResponseTo expectedResponse = new IssueResponseTo(issueId, editor.getEd_id(), "TestTitle", "TestText", new Date(), new Date());

        Sticker sticker = new Sticker(stickerId, "TestSticker", new ArrayList<>());


        when(issueConverter.convertToEntity(issueRequestTo, editor)).thenReturn(issueEntity);
        when(issueRepository.save(issueEntity)).thenReturn(issueEntity);
        when(issueConverter.convertToResponse(issueEntity)).thenReturn(expectedResponse);
        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issueEntity));
        when(editorRepository.findById(editorId)).thenReturn(Optional.of(editor));
        when(stickerRepository.findAllById(Collections.singletonList(stickerId))).thenReturn(Collections.singletonList(sticker));
        when(issueConverter.convertToResponse(issueEntity)).thenReturn(expectedResponse);

        IssueResponseTo result = issueService.updateIssue(issueRequestTo);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(issueConverter, times(1)).convertToEntity(issueRequestTo, editor);
        verify(issueRepository, times(1)).save(issueEntity);
        verify(issueConverter, times(1)).convertToResponse(issueEntity);
    }
}
