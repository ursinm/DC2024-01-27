package com.distributed_computing.rest.service;

import com.distributed_computing.rest.exception.NoSuchComment;
import com.distributed_computing.rest.bean.Comment;
import com.distributed_computing.rest.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private static int ind = 0;

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    public List<Comment> getAll(){
        return commentRepository.getAll();
    }

    public Optional<Comment> getById(int id){
        return commentRepository.getById(id);
    }

    public Comment create(Comment comment){
        comment.setId(ind++);
        commentRepository.save(comment);
        return comment;
    }

    public Comment update(Comment comment){
        if(commentRepository.getById(comment.getId()).isEmpty()) throw new NoSuchComment("There is no such comment with this id");
        commentRepository.save(comment);
        return comment;
    }

    public void delete(int id){
        if(commentRepository.delete(id) == null) throw new NoSuchComment("There is no such comment with this id");
    }

}
