package by.bsuir.discussion.service.comment.impl;

import by.bsuir.discussion.bean.Comment;
import by.bsuir.discussion.bean.Issue;
import by.bsuir.discussion.dto.CommentRequestTo;
import by.bsuir.discussion.dto.CommentResponseTo;
import by.bsuir.discussion.exception.DuplicateEntityException;
import by.bsuir.discussion.exception.EntityNotFoundException;
import by.bsuir.discussion.repository.comment.CommentRepository;
import by.bsuir.discussion.service.comment.ICommentService;
import by.bsuir.discussion.util.converter.CommentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;

    private final CommentConverter commentConverter;

    private final String ENTITY_NAME = "comment";

    private final String URL_ISSUE = "http://localhost:24110/api/v1.0/issues";

    @Autowired
    public CommentService(CommentConverter commentConverter, CommentRepository commentRepository) {
        this.commentConverter = commentConverter;
        this.commentRepository = commentRepository;
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
        checkIsValidIssue(comment.getIssueId());
        Comment entity = commentConverter.convertToEntity(comment);
        if (entity.getCom_id() == null) {
            entity.setCom_id(BigInteger.valueOf(UUID.randomUUID().getMostSignificantBits()).abs());
        }
        try {
            Comment saved = commentRepository.save(entity);
            return commentConverter.convertToResponse(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException(ENTITY_NAME, "");
        }
    }

    @Override
    public void deleteComment(BigInteger id) throws EntityNotFoundException {
        if (commentRepository.findByCom_id(id).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        commentRepository.deleteByCom_id("local", id);
    }

    @Override
    public CommentResponseTo updateComment(CommentRequestTo comment) throws EntityNotFoundException, DuplicateEntityException {
        if (commentRepository.findByCom_id(comment.getId()).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, comment.getId());
        }
        checkIsValidIssue(comment.getIssueId());
        Comment entity = commentConverter.convertToEntity(comment);
        try {
            Comment saved = commentRepository.save(entity);
            return commentConverter.convertToResponse(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException(ENTITY_NAME, "");
        }
    }

    @Override
    public CommentResponseTo getCommentById(BigInteger id) throws EntityNotFoundException {
        Optional<Comment> comment = commentRepository.findByCom_id(id);
        if (comment.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return commentConverter.convertToResponse(comment.get());
    }

    private void checkIsValidIssue(BigInteger issueId) throws EntityNotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.getForObject(URL_ISSUE + "/" + issueId, Issue.class);
        } catch (Exception e) {
            throw new EntityNotFoundException("Issue", issueId);
        }
    }
}
