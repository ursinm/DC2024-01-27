package by.bsuir.publicator.util.converter.issue;

import by.bsuir.publicator.bean.Editor;
import by.bsuir.publicator.bean.Issue;
import by.bsuir.publicator.dto.IssueRequestTo;
import by.bsuir.publicator.dto.IssueResponseTo;
import org.springframework.stereotype.Component;

@Component
public class IssueConverter  {

    public IssueResponseTo convertToResponse(Issue issue) {
        return new IssueResponseTo(issue.getIss_id(), issue.getIss_editor().getEd_id(), issue.getIss_title(), issue.getIss_content(), issue.getIss_created(), issue.getIss_modified());
    }

    public Issue convertToEntity(IssueRequestTo issueRequestTo, Editor editor) {
        return new Issue(issueRequestTo.getId(), editor, issueRequestTo.getTitle(), issueRequestTo.getContent(), issueRequestTo.getCreated(), issueRequestTo.getModified());
    }
}