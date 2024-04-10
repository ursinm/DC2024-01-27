package by.bsuir.publicator.controller;

import by.bsuir.publicator.dto.IssueRequestTo;
import by.bsuir.publicator.dto.IssueResponseTo;
import by.bsuir.publicator.exception.DuplicateEntityException;
import by.bsuir.publicator.exception.EntititesNotFoundException;
import by.bsuir.publicator.exception.EntityNotFoundException;
import by.bsuir.publicator.service.issue.IIssueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class IssueControllerTest {

    @Mock
    private IIssueService issueService;

    @InjectMocks
    private IssueController issueController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetIssues() throws EntititesNotFoundException {
        when(issueService.getIssues()).thenReturn(Collections.singletonList(new IssueResponseTo()));

        List<IssueResponseTo> result = issueController.getIssues();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetIssueById() throws EntititesNotFoundException, EntityNotFoundException {
        BigInteger id = BigInteger.valueOf(1L);
        when(issueService.getIssueById(id)).thenReturn(new IssueResponseTo());

        IssueResponseTo result = issueController.getIssueById(id);

        assertNotNull(result);
    }

    @Test
    void testCreateIssue() throws EntititesNotFoundException, EntityNotFoundException, DuplicateEntityException {
        IssueRequestTo issueRequest = new IssueRequestTo();
        when(issueService.addIssue(issueRequest)).thenReturn(new IssueResponseTo());

        IssueResponseTo result = issueController.createIssue(issueRequest);

        assertNotNull(result);
    }

    @Test
    void testUpdateIssue() throws EntititesNotFoundException, EntityNotFoundException, DuplicateEntityException {
        IssueRequestTo issueRequest = new IssueRequestTo();
        when(issueService.updateIssue(issueRequest)).thenReturn(new IssueResponseTo());

        IssueResponseTo result = issueController.updateIssue(issueRequest);

        assertNotNull(result);
    }

    @Test
    void testDeleteIssue() throws EntityNotFoundException {
        BigInteger id = BigInteger.valueOf(1L);

        issueController.deleteIssue(id);

        verify(issueService, times(1)).deleteIssue(id);
    }
}
