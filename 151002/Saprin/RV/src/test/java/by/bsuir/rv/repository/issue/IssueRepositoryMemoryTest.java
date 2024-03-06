package by.bsuir.rv.repository.issue;

import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.repository.exception.RepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IssueRepositoryMemoryTest {

    @InjectMocks
    private IssueRepositoryMemory issueRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_shouldSaveIssue() {
        Issue issue = new Issue();

        Issue savedIssue = issueRepository.save(issue);

        assertNotNull(savedIssue.getIss_id());
        assertEquals(1, issueRepository.findAll().size());
    }

    @Test
    void testFindAll_shouldReturnAllIssues() {
        Issue issue1 = new Issue();
        Issue issue2 = new Issue();
        issueRepository.save(issue1);
        issueRepository.save(issue2);

        List<Issue> issues = issueRepository.findAll();

        assertEquals(2, issues.size());
    }

    @Test
    void testFindById_shouldReturnIssueById() throws RepositoryException {
        Issue issue = new Issue();
        Issue savedIssue = issueRepository.save(issue);

        Issue foundIssue = issueRepository.findById(savedIssue.getIss_id());

        assertNotNull(foundIssue);
        assertEquals(savedIssue.getIss_id(), foundIssue.getIss_id());
    }

    @Test
    void testFindById_shouldThrowExceptionIfIssueNotFound() {
        BigInteger nonExistingId = BigInteger.valueOf(999);

        assertThrows(RepositoryException.class, () -> issueRepository.findById(nonExistingId));
    }

    @Test
    void testFindAllById_shouldReturnIssuesByIds() throws RepositoryException {
        Issue issue1 = new Issue();
        Issue issue2 = new Issue();
        Issue savedIssue1 = issueRepository.save(issue1);
        Issue savedIssue2 = issueRepository.save(issue2);

        List<BigInteger> idsToFind = Arrays.asList(savedIssue1.getIss_id(), savedIssue2.getIss_id());

        List<Issue> foundIssues = issueRepository.findAllById(idsToFind);

        assertEquals(2, foundIssues.size());
    }

    @Test
    void testFindAllById_shouldThrowExceptionIfIssuesNotFound() {
        BigInteger nonExistingId1 = BigInteger.valueOf(999);
        BigInteger nonExistingId2 = BigInteger.valueOf(1000);
        List<BigInteger> nonExistingIds = Arrays.asList(nonExistingId1, nonExistingId2);

        assertThrows(RepositoryException.class, () -> issueRepository.findAllById(nonExistingIds));
    }

    @Test
    void testDeleteById_shouldDeleteIssueById() throws RepositoryException {
        Issue issue = new Issue();
        Issue savedIssue = issueRepository.save(issue);

        issueRepository.deleteById(savedIssue.getIss_id());

        assertEquals(0, issueRepository.findAll().size());
    }

    @Test
    void testDeleteById_shouldThrowExceptionIfIssueNotFound() {
        BigInteger nonExistingId = BigInteger.valueOf(999);

        assertThrows(RepositoryException.class, () -> issueRepository.deleteById(nonExistingId));
    }
}
