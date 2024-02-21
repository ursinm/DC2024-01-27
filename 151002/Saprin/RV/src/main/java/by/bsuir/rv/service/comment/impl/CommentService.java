package by.bsuir.rv.service.comment.impl;

import by.bsuir.rv.bean.Comment;
import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.dto.CommentRequestTo;
import by.bsuir.rv.dto.CommentResponseTo;
import by.bsuir.rv.exception.EntititesNotFoundException;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.repository.comment.CommentRepositoryMemory;
import by.bsuir.rv.repository.exception.RepositoryException;
import by.bsuir.rv.repository.issue.IssueRepositoryMemory;
import by.bsuir.rv.service.comment.ICommentService;
import by.bsuir.rv.util.converter.comment.CommentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService implements ICommentService {

    private final CommentConverter commentConverter;
    private final CommentRepositoryMemory commentRepositoryMemory;
    private final IssueRepositoryMemory issueRepositoryMemory;

    private final String ENTITY_NAME = "comment";

    @Autowired
    public CommentService(CommentConverter commentConverter, CommentRepositoryMemory commentRepositoryMemory, IssueRepositoryMemory issueRepositoryMemory) {
        this.commentConverter = commentConverter;
        this.commentRepositoryMemory = commentRepositoryMemory;
        this.issueRepositoryMemory = issueRepositoryMemory;
    }

    @Override
    public List<CommentResponseTo> getComments() throws EntititesNotFoundException {
        List<Comment> comments = commentRepositoryMemory.findAll();
        List<BigInteger> ids = comments.stream().map(Comment::getIssueId).toList();
        List<Issue> issues;
        try {
             issues = issueRepositoryMemory.findAllById(ids);
        } catch (RepositoryException e) {
            throw new EntititesNotFoundException(ENTITY_NAME, ids);
        }

        List<CommentResponseTo> result = new ArrayList<>();
        for (Comment comment : comments) {
            result.add(commentConverter.convertToResponse(comment));
        }

        return result;
    }

    @Override
    public CommentResponseTo addComment(CommentRequestTo comment) {
        Comment entity = commentConverter.convertToEntity(comment);
        Comment saved = commentRepositoryMemory.save(entity);
        return commentConverter.convertToResponse(saved);
    }

    @Override
    public void deleteComment(BigInteger id) throws EntityNotFoundException {
        try {
            commentRepositoryMemory.deleteById(id);
        } catch (RepositoryException e) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
    }

    @Override
    public CommentResponseTo updateComment(CommentRequestTo comment) throws EntityNotFoundException {
        try {
            commentRepositoryMemory.findById(comment.getId());
        } catch (RepositoryException e) {
            throw new EntityNotFoundException(ENTITY_NAME, comment.getId());
        }
        Comment entity = commentConverter.convertToEntity(comment);
        Comment saved = commentRepositoryMemory.save(entity);
        return commentConverter.convertToResponse(saved);
    }

    @Override
    public CommentResponseTo getCommentById(BigInteger id) throws EntityNotFoundException {
        Comment comment;
        Issue issue;
        try {
            comment = commentRepositoryMemory.findById(id);
        } catch (RepositoryException e) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }

        try {
            issue = issueRepositoryMemory.findById(comment.getIssueId());
        } catch (RepositoryException e) {
            throw new EntityNotFoundException("Issue", id);
        }
        return commentConverter.convertToResponse(comment);
    }
}
