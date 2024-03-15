package by.bsuir.rv.util.converter.issue;

import by.bsuir.rv.bean.Editor;
import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.bean.Sticker;
import by.bsuir.rv.dto.IssueRequestTo;
import by.bsuir.rv.dto.IssueResponseTo;
import by.bsuir.rv.dto.StickerRequestTo;
import by.bsuir.rv.dto.StickerResponseTo;
import by.bsuir.rv.util.converter.IConverter;
import by.bsuir.rv.util.converter.sticker.StickerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class IssueConverter implements IConverter<Issue, IssueResponseTo, IssueRequestTo> {

    public IssueResponseTo convertToResponse(Issue issue) {
        return new IssueResponseTo(issue.getId(), issue.getEditorId(), issue.getTitle(), issue.getContent(), issue.getCreated(), issue.getModified());
    }

    @Override
    public Issue convertToEntity(IssueRequestTo issueRequestTo) {
        return new Issue(issueRequestTo.getId(), issueRequestTo.getEditorId(), issueRequestTo.getTitle(), issueRequestTo.getContent(), issueRequestTo.getCreated(), issueRequestTo.getModified());
    }
}