package by.bsuir.rv.service.issue;

import by.bsuir.rv.bean.Editor;
import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.bean.Sticker;
import by.bsuir.rv.dto.IssueRequestTo;
import by.bsuir.rv.dto.IssueResponseTo;
import by.bsuir.rv.exception.EntititesNotFoundException;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.repository.exception.RepositoryException;
import by.bsuir.rv.service.issue.impl.IssueService;
import by.bsuir.rv.util.converter.issue.IssueConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IssueServiceTest {

    @Mock
    private IssueRepositoryMemory issueRepository;

    @Mock
    private EditorRepositoryMemory editorRepository;

    @Mock
    private StickerRepositoryMemory stickerRepository;

    @Mock
    private IssueConverter issueConverter;

    @InjectMocks
    private IssueService issueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getIssues_shouldReturnListOfIssues() throws EntititesNotFoundException, RepositoryException {
        BigInteger editorId = BigInteger.valueOf(1);
        BigInteger stickerId = BigInteger.valueOf(2);
        BigInteger issueId = BigInteger.valueOf(3);

        Editor editor = new Editor(editorId, "TestEditor", "Password", "First", "Last");
        Issue issue = new Issue(issueId, editorId, "TestTitle", "TestText", new Date(), new Date());
        Sticker sticker = new Sticker(stickerId, "TestSticker", issueId);

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
    void getIssueById_shouldReturnIssueById() throws EntityNotFoundException, RepositoryException {
        BigInteger editorId = BigInteger.valueOf(1);
        BigInteger stickerId = BigInteger.valueOf(2);
        BigInteger issueId = BigInteger.valueOf(3);

        Editor editor = new Editor(editorId, "TestEditor", "Password", "First", "Last");
        Issue issue = new Issue(issueId, editorId, "TestTitle", "TestText", new Date(), new Date());
        Sticker sticker = new Sticker(stickerId, "TestSticker", issueId);

        when(issueRepository.findById(issueId)).thenReturn(issue);
        when(editorRepository.findById(editorId)).thenReturn(editor);
        when(stickerRepository.findAllById(Collections.singletonList(stickerId))).thenReturn(Collections.singletonList(sticker));
        when(issueConverter.convertToResponse(issue)).thenReturn(new IssueResponseTo());

        IssueResponseTo result = issueService.getIssueById(issueId);

        assertNotNull(result);
        verify(issueRepository, times(1)).findById(issueId);
        verify(issueConverter, times(1)).convertToResponse(issue);
    }

    @Test
    void addIssue_shouldAddIssueAndReturnResponse() throws EntityNotFoundException, RepositoryException {
        BigInteger editorId = BigInteger.valueOf(1);
        BigInteger stickerId = BigInteger.valueOf(2);
        BigInteger issueId = BigInteger.valueOf(3);

        Editor editor = new Editor(editorId, "TestEditor", "Password", "First", "Last");
        IssueRequestTo issueRequestTo = new IssueRequestTo(issueId, editor.getEd_id(), "TestTitle", "TestText", new Date(), new Date());
        Issue issueEntity = new Issue(issueId, editorId, "TestTitle", "TestText", new Date(), new Date());
        IssueResponseTo expectedResponse = new IssueResponseTo(issueId, editor.getEd_id(), "TestTitle", "TestText", new Date(), new Date());

        Sticker sticker = new Sticker(stickerId, "TestSticker", issueId);


        when(issueConverter.convertToEntity(issueRequestTo)).thenReturn(issueEntity);
        when(issueRepository.save(issueEntity)).thenReturn(issueEntity);
        when(issueConverter.convertToResponse(issueEntity)).thenReturn(expectedResponse);
        when(issueRepository.findById(issueId)).thenReturn(issueEntity);
        when(editorRepository.findById(editorId)).thenReturn(editor);
        when(stickerRepository.findAllById(Collections.singletonList(stickerId))).thenReturn(Collections.singletonList(sticker));
        when(issueConverter.convertToResponse(issueEntity)).thenReturn(expectedResponse);

        IssueResponseTo result = issueService.addIssue(issueRequestTo);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(issueConverter, times(1)).convertToEntity(issueRequestTo);
        verify(issueRepository, times(1)).save(issueEntity);
        verify(issueConverter, times(1)).convertToResponse(issueEntity);
    }


    @Test
    void deleteIssue_shouldDeleteIssueById() throws RepositoryException {
        BigInteger issueId = BigInteger.valueOf(1);

        doNothing().when(issueRepository).deleteById(issueId);

        assertDoesNotThrow(() -> issueService.deleteIssue(issueId));

        verify(issueRepository, times(1)).deleteById(issueId);
    }

    @Test
    void updateIssue_shouldUpdateIssueAndReturnResponse() throws EntityNotFoundException, EntititesNotFoundException, RepositoryException {
        BigInteger editorId = BigInteger.valueOf(1);
        BigInteger stickerId = BigInteger.valueOf(2);
        BigInteger issueId = BigInteger.valueOf(3);

        Editor editor = new Editor(editorId, "TestEditor", "Password", "First", "Last");

        IssueRequestTo issueRequestTo = new IssueRequestTo(issueId, editor.getEd_id(), "TestTitle", "TestText", new Date(), new Date());
        Issue issueEntity = new Issue(issueId, editorId, "TestTitle", "TestText", new Date(), new Date());
        IssueResponseTo expectedResponse = new IssueResponseTo(issueId, editor.getEd_id(), "TestTitle", "TestText", new Date(), new Date());

        Sticker sticker = new Sticker(stickerId, "TestSticker", issueId);


        when(issueConverter.convertToEntity(issueRequestTo)).thenReturn(issueEntity);
        when(issueRepository.save(issueEntity)).thenReturn(issueEntity);
        when(issueConverter.convertToResponse(issueEntity)).thenReturn(expectedResponse);
        when(issueRepository.findById(issueId)).thenReturn(issueEntity);
        when(editorRepository.findById(editorId)).thenReturn(editor);
        when(stickerRepository.findAllById(Collections.singletonList(stickerId))).thenReturn(Collections.singletonList(sticker));
        when(issueConverter.convertToResponse(issueEntity)).thenReturn(expectedResponse);

        IssueResponseTo result = issueService.updateIssue(issueRequestTo);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(issueConverter, times(1)).convertToEntity(issueRequestTo);
        verify(issueRepository, times(1)).save(issueEntity);
        verify(issueConverter, times(1)).convertToResponse(issueEntity);
    }
}
