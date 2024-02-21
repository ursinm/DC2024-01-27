package by.bsuir.rv.service.issue;

import by.bsuir.rv.dto.IssueRequestTo;
import by.bsuir.rv.dto.IssueResponseTo;
import by.bsuir.rv.exception.EntititesNotFoundException;
import by.bsuir.rv.exception.EntityNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface IIssueService {
    List<IssueResponseTo> getIssues() throws EntititesNotFoundException;
    IssueResponseTo getIssueById(BigInteger id) throws EntititesNotFoundException, EntityNotFoundException;
    IssueResponseTo addIssue(IssueRequestTo issue) throws EntititesNotFoundException, EntityNotFoundException;
    void deleteIssue(BigInteger id) throws EntityNotFoundException;
    IssueResponseTo updateIssue(IssueRequestTo issue) throws EntityNotFoundException, EntititesNotFoundException;

}
