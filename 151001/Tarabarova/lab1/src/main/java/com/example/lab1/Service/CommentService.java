package com.example.lab1.Service;

import com.example.lab1.DAO.CommentDao;
import com.example.lab1.DTO.CommentRequestTo;
import com.example.lab1.DTO.CommentResponseTo;
import com.example.lab1.Exception.NotFoundException;
import com.example.lab1.Mapper.CommentListMapper;
import com.example.lab1.Mapper.CommentMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class CommentService {
    @Autowired
    CommentDao commentDao;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentListMapper commentListMapper;

    public CommentResponseTo create(@Valid CommentRequestTo commentRequestTo){
        return commentMapper.commentToCommentResponse(commentDao.create(commentMapper.commentRequestToComment(commentRequestTo)));
    }
    public CommentResponseTo read(@Min(0) int id) throws NotFoundException {
        CommentResponseTo commentResponseTo = commentMapper.commentToCommentResponse(commentDao.read(id));
        if(commentResponseTo != null)
            return commentResponseTo;
        else
            throw new NotFoundException("Comment not found", 404);
    }
    public List<CommentResponseTo> readAll(){
        return commentListMapper.toCommentResponseList(commentDao.readAll());
    }
    public CommentResponseTo update(@Valid CommentRequestTo commentRequestTo, @Min(0) int id) throws NotFoundException {
        CommentResponseTo commentResponseTo = commentMapper.commentToCommentResponse(commentDao.update(commentMapper.commentRequestToComment(commentRequestTo),id));
        if(commentResponseTo != null)
            return commentResponseTo;
        else
            throw new NotFoundException("Comment not found", 404);
    }
    public boolean delete(@Min(0) int id) throws NotFoundException {
        boolean isDeleted = commentDao.delete(id);
        if(isDeleted)
            return true;
        else
            throw new NotFoundException("Comment not found", 404);
    }

}
