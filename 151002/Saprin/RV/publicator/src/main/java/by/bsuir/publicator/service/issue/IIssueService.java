package by.bsuir.publicator.service.issue;

import by.bsuir.publicator.dto.IssueRequestTo;
import by.bsuir.publicator.dto.IssueResponseTo;
import by.bsuir.publicator.exception.DuplicateEntityException;
import by.bsuir.publicator.exception.EntititesNotFoundException;
import by.bsuir.publicator.exception.EntityNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface IIssueService {
    List<IssueResponseTo> getIssues() throws EntititesNotFoundException;
    IssueResponseTo getIssueById(BigInteger id) throws EntititesNotFoundException, EntityNotFoundException;
    IssueResponseTo addIssue(IssueRequestTo issue) throws EntititesNotFoundException, EntityNotFoundException, DuplicateEntityException;
    void deleteIssue(BigInteger id) throws EntityNotFoundException;
    IssueResponseTo updateIssue(IssueRequestTo issue) throws EntityNotFoundException, EntititesNotFoundException, DuplicateEntityException;

}
