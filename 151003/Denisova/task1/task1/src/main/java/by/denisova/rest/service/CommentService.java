package by.denisova.rest.service;

import by.denisova.rest.model.Comment;

import java.util.List;

public interface CommentService {

    Comment findById(Long id);

    void deleteById(Long id);

    Comment save(Comment comment);

    List<Comment> findAll();

    Comment update(Comment comment);
}
