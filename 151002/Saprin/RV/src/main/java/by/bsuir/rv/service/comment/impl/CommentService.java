package by.bsuir.rv.service.comment.impl;

import by.bsuir.rv.bean.Comment;
import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.dto.CommentRequestTo;
import by.bsuir.rv.dto.CommentResponseTo;
import by.bsuir.rv.exception.DuplicateEntityException;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.repository.comment.CommentRepository;
import by.bsuir.rv.repository.issue.IssueRepository;
import by.bsuir.rv.service.comment.ICommentService;
import by.bsuir.rv.util.converter.comment.CommentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;

    private final CommentConverter commentConverter;

    private final String ENTITY_NAME = "comment";

    @Autowired
    public CommentService(CommentConverter commentConverter, CommentRepository commentRepository, IssueRepository issueRepository) {
        this.commentConverter = commentConverter;
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
    }

    @Override
    public List<CommentResponseTo> getComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentResponseTo> responses = new ArrayList<>();
        for (Comment comment : comments) {
            responses.add(commentConverter.convertToResponse(comment));
        }
        return responses;
    }

    @Override
    public CommentResponseTo addComment(CommentRequestTo comment) throws EntityNotFoundException, DuplicateEntityException {
        Optional<Issue> issue = issueRepository.findById(comment.getIssueId());
        if (issue.isEmpty()) {
            throw new EntityNotFoundException("Issue", comment.getIssueId());
        }
        Comment entity = commentConverter.convertToEntity(comment, issue.get());
        try {
            Comment saved = commentRepository.save(entity);
            return commentConverter.convertToResponse(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException(ENTITY_NAME, "");
        }
    }

    @Override
    public void deleteComment(BigInteger id) throws EntityNotFoundException {
        if (commentRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        commentRepository.deleteById(id);
    }

    @Override
    public CommentResponseTo updateComment(CommentRequestTo comment) throws EntityNotFoundException, DuplicateEntityException {
        if (commentRepository.findById(comment.getId()).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, comment.getId());
        }
        Optional<Issue> issue = issueRepository.findById(comment.getIssueId());
        if (issue.isEmpty()) {
            throw new EntityNotFoundException("Issue", comment.getIssueId());
        }
        Comment entity = commentConverter.convertToEntity(comment, issue.get());
        try {
            Comment saved = commentRepository.save(entity);
            return commentConverter.convertToResponse(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException(ENTITY_NAME, "");
        }
    }

    @Override
    public CommentResponseTo getCommentById(BigInteger id) throws EntityNotFoundException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return commentConverter.convertToResponse(comment.get());
    }
}
