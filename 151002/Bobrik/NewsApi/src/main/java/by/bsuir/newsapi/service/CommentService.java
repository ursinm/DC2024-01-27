package by.bsuir.newsapi.service;

import by.bsuir.newsapi.dao.CommentRepository;
import by.bsuir.newsapi.model.entity.Comment;
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
        return commentMapper.getListResponseTo(commentRepository.findAll());
    }

    @Override
    public CommentResponseTo findById(Long id) {
        return commentMapper.getResponseTo(commentRepository
                .findById(id)
                .orElseThrow(() -> commentNotFoundException(id)));
    }

    @Override
    public CommentResponseTo create(CommentRequestTo commentTo) {
        return commentMapper.getResponseTo(commentRepository.save(commentMapper.getComment(commentTo)));
    }

    @Override
    public CommentResponseTo update(CommentRequestTo commentTo) {
        commentRepository
                .findById(commentMapper.getComment(commentTo).getId())
                .orElseThrow(() -> commentNotFoundException(commentMapper.getComment(commentTo).getId()));
        return commentMapper.getResponseTo(commentRepository.save(commentMapper.getComment(commentTo)));
    }

    @Override
    public void removeById(Long id) {
        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(() -> commentNotFoundException(id));
        commentRepository.delete(comment);
    }

    private static ResourceNotFoundException commentNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find comment with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 83);
    }
}
