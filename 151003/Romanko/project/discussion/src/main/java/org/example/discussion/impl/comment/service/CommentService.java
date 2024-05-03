package org.example.discussion.impl.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.discussion.api.exception.DuplicateEntityException;
import org.example.discussion.api.exception.EntityNotFoundException;
import org.example.discussion.impl.comment.Comment;
import org.example.discussion.impl.comment.CommentRepository;
import org.example.discussion.impl.comment.dto.CommentRequestTo;
import org.example.discussion.impl.comment.dto.CommentResponseTo;
import org.example.discussion.impl.comment.mapper.Impl.CommentMapperImpl;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapperImpl commentMapper;
    private final String ENTITY_NAME = "comment";

    private final String TWEET_PATH = "http://localhost:24110/api/v1.0/storys/";


    public List<CommentResponseTo> getComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentResponseTo> commentsTo = new ArrayList<>();
        for (var item : comments) {
            commentsTo.add(commentMapper.commentToResponseTo(item));
        }

        return commentsTo;
    }

    public CommentResponseTo getCommentById(BigInteger id) throws EntityNotFoundException {
        Optional<Comment> comment = commentRepository.findBy_id("local", id);
        if (comment.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return commentMapper.commentToResponseTo(comment.get());
    }

    public CommentResponseTo saveComment(CommentRequestTo commentTO) throws EntityNotFoundException, DuplicateEntityException {

        Comment comment = commentRepository.save(commentMapper.dtoToEntity(commentTO, "local"));
        return commentMapper.commentToResponseTo(comment);

    }

    public CommentResponseTo updateComment(CommentRequestTo commentTO) throws EntityNotFoundException, DuplicateEntityException {
        Comment comment = commentRepository.save(commentMapper.dtoToEntity(commentTO, "local"));
        return commentMapper.commentToResponseTo(comment);

    }

    public void deleteComment(BigInteger id) throws EntityNotFoundException {
        if (commentRepository.findBy_id("local", id).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        commentRepository.deleteBy_id("local", id);
    }
}
