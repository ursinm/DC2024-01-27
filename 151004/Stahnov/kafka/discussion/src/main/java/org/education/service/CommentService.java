package org.education.service;


import org.education.bean.Comment;
import org.education.exception.NoSuchComment;
import org.education.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final String country = "Belarus";

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAll(){
        return StreamSupport.stream(commentRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Comment getById(int id){
        return commentRepository.findCommentByKey(id).orElseThrow(() -> new NoSuchComment("No such comment with id"));
    }

    public Comment create(Comment comment){
        comment.setCountry("Belarus");
        return commentRepository.save(comment);
    }

    public Comment update(Comment comment){
        if(!commentRepository.existsByKey(comment.getKey())) throw new NoSuchComment("There is no such comment with this id");
        comment.setCountry(commentRepository.findCommentByKey(comment.getKey()).get().getCountry());
        return commentRepository.save(comment);
    }

    public void delete(int id){
        if(!commentRepository.existsByKey(id)) throw new NoSuchComment("There is no such comment with this id");
        commentRepository.delete(commentRepository.findCommentByKey(id).get());
    }

}
