package com.distributed_computing.jpa.service;

import com.distributed_computing.jpa.bean.Comment;
import com.distributed_computing.jpa.exception.IncorrectValuesException;
import com.distributed_computing.jpa.exception.NoSuchComment;
import com.distributed_computing.jpa.repository.CommentRepository;
import com.distributed_computing.jpa.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TweetRepository tweetRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, TweetRepository tweetRepository) {
        this.commentRepository = commentRepository;
        this.tweetRepository = tweetRepository;
    }

    public List<Comment> getAll(){
        return commentRepository.findAll();
    }

    public Comment getById(int id){
        return commentRepository.getReferenceById(id);
    }

    public Comment create(Comment comment, int ownerId){
        if(!tweetRepository.existsById(ownerId)) throw new IncorrectValuesException("There is no tweets with this id");
        commentRepository.save(comment);
        return comment;
    }

    public Comment update(Comment comment){
        if(!commentRepository.existsById(comment.getId())) throw new NoSuchComment("There is no such comment with this id");
        commentRepository.save(comment);
        return comment;
    }

    public void delete(int id){
        if(!commentRepository.existsById(id)) throw new NoSuchComment("There is no such comment with this id");
        commentRepository.deleteById(id);
    }

}
