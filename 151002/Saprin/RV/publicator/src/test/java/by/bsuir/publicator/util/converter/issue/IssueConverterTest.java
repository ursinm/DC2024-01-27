package by.bsuir.publicator.util.converter.issue;

import by.bsuir.publicator.bean.Editor;
import by.bsuir.publicator.bean.Issue;
import by.bsuir.publicator.bean.Sticker;
import by.bsuir.publicator.dto.IssueRequestTo;
import by.bsuir.publicator.dto.IssueResponseTo;
import by.bsuir.publicator.dto.StickerResponseTo;
import by.bsuir.publicator.util.converter.sticker.StickerConverter;
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

    private final Issue issue = new Issue(BigInteger.ONE, new Editor(), "Title", "Text", new Date(), new Date());

    @Test
    void convertToResponse_shouldConvertIssueToResponse() {
        MockitoAnnotations.openMocks(this);
        when(stickerConverter.convertToResponse(any(Sticker.class))).thenReturn(new StickerResponseTo());

        IssueResponseTo response = issueConverter.convertToResponse(issue);

        assertNotNull(response);
        assertEquals(issue.getIss_id(), response.getId());
        assertEquals(issue.getIss_title(), response.getTitle());
        assertEquals(issue.getIss_content(), response.getContent());
        assertEquals(issue.getIss_created(), response.getCreated());
        assertEquals(issue.getIss_modified(), response.getModified());
    }

    @Test
    void convertToEntity_shouldConvertIssueRequestToIssue() {

        IssueRequestTo issueRequestTo = new IssueRequestTo(BigInteger.ONE, BigInteger.ONE, "Test Issue", "Test Text", null, null);

        MockitoAnnotations.openMocks(this);

        Issue issue = issueConverter.convertToEntity(issueRequestTo, new Editor(BigInteger.ONE, "Test Editor", "Password", "First", "Last"));

        assertNotNull(issue);
        assertEquals(issueRequestTo.getId(), issue.getIss_id());
        assertEquals(issueRequestTo.getEditorId(), issue.getIss_editor().getEd_id());
        assertEquals(issueRequestTo.getTitle(), issue.getIss_title());
        assertEquals(issueRequestTo.getContent(), issue.getIss_content());
        assertEquals(issueRequestTo.getCreated(), issue.getIss_created());
        assertEquals(issueRequestTo.getModified(), issue.getIss_modified());
    }
}
