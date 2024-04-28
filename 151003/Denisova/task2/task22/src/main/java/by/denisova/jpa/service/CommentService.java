package by.denisova.jpa.service;

import by.denisova.jpa.model.Comment;

import java.util.List;

public interface CommentService {

    Comment findById(Long id);

    void deleteById(Long id);

    Comment save(Comment comment);

    List<Comment> findAll();

    Comment update(Comment comment);
}
