package by.bsuir.discussion.service.comment;

import by.bsuir.discussion.dto.CommentRequestTo;
import by.bsuir.discussion.dto.CommentResponseTo;
import by.bsuir.discussion.exception.DuplicateEntityException;
import by.bsuir.discussion.exception.EntititesNotFoundException;
import by.bsuir.discussion.exception.EntityNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface ICommentService {
    List<CommentResponseTo> getComments() throws EntititesNotFoundException;

    CommentResponseTo addComment(CommentRequestTo comment) throws EntityNotFoundException, DuplicateEntityException;
    void deleteComment(BigInteger id) throws EntityNotFoundException;
    CommentResponseTo updateComment(CommentRequestTo comment) throws EntityNotFoundException, DuplicateEntityException;
    CommentResponseTo getCommentById(BigInteger id) throws EntityNotFoundException;
}
