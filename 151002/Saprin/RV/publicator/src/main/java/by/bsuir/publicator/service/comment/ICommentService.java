package by.bsuir.publicator.service.comment;

import by.bsuir.publicator.dto.CommentAddResponseTo;
import by.bsuir.publicator.dto.CommentRequestTo;
import by.bsuir.publicator.dto.CommentResponseTo;
import by.bsuir.publicator.exception.DuplicateEntityException;
import by.bsuir.publicator.exception.EntititesNotFoundException;
import by.bsuir.publicator.exception.EntityNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ICommentService {
    List<CommentResponseTo> getComments() throws EntititesNotFoundException;

    CommentAddResponseTo addComment(CommentRequestTo comment) throws EntityNotFoundException, DuplicateEntityException, JsonProcessingException, InterruptedException;
    void deleteComment(BigInteger id) throws EntityNotFoundException, InterruptedException;
    CommentResponseTo updateComment(CommentRequestTo comment) throws EntityNotFoundException, DuplicateEntityException, JsonProcessingException, InterruptedException;
    CommentResponseTo getCommentById(BigInteger id) throws EntityNotFoundException, ExecutionException, InterruptedException;
}
