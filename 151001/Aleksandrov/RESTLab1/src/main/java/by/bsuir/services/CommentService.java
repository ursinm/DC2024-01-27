package by.bsuir.services;

import by.bsuir.dao.CommentDao;
import by.bsuir.dto.CommentRequestTo;
import by.bsuir.dto.CommentResponseTo;
import by.bsuir.entities.Comment;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.CommentListMapper;
import by.bsuir.mapper.CommentMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentDao commentDao;
    @Autowired
    CommentListMapper commentListMapper;

    public CommentResponseTo getCommentById(@Min(0) Long id) throws NotFoundException {
        Optional<Comment> comment = commentDao.findById(id);
        return comment.map(value -> commentMapper.commentToCommentResponse(value)).orElseThrow(() -> new NotFoundException("Comment not found!", 40004L));
    }

    public List<CommentResponseTo> getComments() {
        return commentListMapper.toCommentResponseList(commentDao.findAll());
    }

    public CommentResponseTo saveComment(@Valid CommentRequestTo comment) {
        Comment commentToSave = commentMapper.commentRequestToComment(comment);
        return commentMapper.commentToCommentResponse(commentDao.save(commentToSave));
    }

    public void deleteComment(@Min(0) Long id) throws DeleteException {
        commentDao.delete(id);
    }

    public CommentResponseTo updateComment(@Valid CommentRequestTo comment) throws UpdateException {
        Comment commentToUpdate = commentMapper.commentRequestToComment(comment);
        return commentMapper.commentToCommentResponse(commentDao.update(commentToUpdate));
    }

    public CommentResponseTo getCommentByIssueId(@Min(0) Long issueId) throws NotFoundException {
        Optional<Comment> comment = commentDao.getCommentByIssueId(issueId);
        return comment.map(value -> commentMapper.commentToCommentResponse(value)).orElseThrow(() -> new NotFoundException("Comment not found!", 40004L));
    }
}
