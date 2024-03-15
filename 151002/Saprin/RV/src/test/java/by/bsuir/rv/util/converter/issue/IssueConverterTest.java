package by.bsuir.rv.util.converter.issue;

import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.bean.Sticker;
import by.bsuir.rv.dto.IssueRequestTo;
import by.bsuir.rv.dto.IssueResponseTo;
import by.bsuir.rv.dto.StickerRequestTo;
import by.bsuir.rv.dto.StickerResponseTo;
import by.bsuir.rv.util.converter.sticker.StickerConverter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IssueConverterTest {

    @Mock
    private StickerConverter stickerConverter;

    @InjectMocks
    private IssueConverter issueConverter;

    private final Issue issue = new Issue(BigInteger.ONE, BigInteger.ONE, "Title", "Text", new Date(), new Date());

    @Test
    void convertToResponse_shouldConvertIssueToResponse() {
        MockitoAnnotations.openMocks(this);
        when(stickerConverter.convertToResponse(any(Sticker.class))).thenReturn(new StickerResponseTo());

        IssueResponseTo response = issueConverter.convertToResponse(issue);

        assertNotNull(response);
        assertEquals(issue.getId(), response.getId());
        assertEquals(issue.getTitle(), response.getTitle());
        assertEquals(issue.getContent(), response.getContent());
        assertEquals(issue.getCreated(), response.getCreated());
        assertEquals(issue.getModified(), response.getModified());
    }

    @Test
    void convertToEntity_shouldConvertIssueRequestToIssue() {

        IssueRequestTo issueRequestTo = new IssueRequestTo(BigInteger.ONE, BigInteger.ONE, "Test Issue", "Test Text", null, null);

        MockitoAnnotations.openMocks(this);
        when(stickerConverter.convertToEntity(any(StickerRequestTo.class))).thenReturn(new Sticker());

        Issue issue = issueConverter.convertToEntity(issueRequestTo);

        assertNotNull(issue);
        assertEquals(issueRequestTo.getId(), issue.getId());
        assertEquals(issueRequestTo.getEditorId(), issue.getEditorId());
        assertEquals(issueRequestTo.getTitle(), issue.getTitle());
        assertEquals(issueRequestTo.getContent(), issue.getContent());
        assertEquals(issueRequestTo.getCreated(), issue.getCreated());
        assertEquals(issueRequestTo.getModified(), issue.getModified());
    }
}
