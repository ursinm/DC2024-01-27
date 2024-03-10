package by.bsuir.newsapi.service;

import by.bsuir.newsapi.dao.impl.CommentRepository;
import by.bsuir.newsapi.model.request.CommentRequestTo;
import by.bsuir.newsapi.model.response.CommentResponseTo;
import by.bsuir.newsapi.service.exceptions.ResourceNotFoundException;
import by.bsuir.newsapi.service.exceptions.ResourceStateException;
import by.bsuir.newsapi.service.mapper.CommentMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class CommentService implements RestService<CommentRequestTo, CommentResponseTo> {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<CommentResponseTo> findAll() {
        return commentMapper.getListResponseTo(commentRepository.getAll());
    }

    @Override
    public CommentResponseTo findById(Long id) {
        return commentMapper.getResponseTo(commentRepository
                .getBy(id)
                .orElseThrow(() -> commentNotFoundException(id)));
    }

    @Override
    public CommentResponseTo create(CommentRequestTo commentTo) {
        return commentRepository
                .save(commentMapper.getComment(commentTo))
                .map(commentMapper::getResponseTo)
                .orElseThrow(CommentService::newsStateException);
    }

    @Override
    public CommentResponseTo update(CommentRequestTo commentTo) {
        commentRepository
                .getBy(commentMapper.getComment(commentTo).getId())
                .orElseThrow(() -> commentNotFoundException(commentMapper.getComment(commentTo).getId()));
        return commentRepository
                .update(commentMapper.getComment(commentTo))
                .map(commentMapper::getResponseTo)
                .orElseThrow(CommentService::newsStateException);
    }

    @Override
    public boolean removeById(Long id) {
        if (!commentRepository.removeById(id)) {
            throw commentNotFoundException(id);
        }
        return true;
    }

    private static ResourceNotFoundException commentNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find comment with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 83);
    }

    private static ResourceStateException newsStateException() {
        return new ResourceStateException("Failed to create/update comment with specified credentials", HttpStatus.CONFLICT.value() * 100 + 84);
    }
}
