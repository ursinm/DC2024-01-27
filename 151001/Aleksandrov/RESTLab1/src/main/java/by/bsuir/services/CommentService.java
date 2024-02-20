package by.bsuir.services;

import by.bsuir.dao.CommentDao;
import by.bsuir.dto.CommentRequestTo;
import by.bsuir.dto.CommentResponseTo;
import by.bsuir.entities.Comment;
import by.bsuir.mapper.CommentListMapper;
import by.bsuir.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentDao commentDao;
    @Autowired
    CommentListMapper commentListMapper;

    public CommentResponseTo getCommentById(Long id) {
        Optional<Comment> comment = commentDao.findById(id);
        return comment.map(value -> commentMapper.commentToCommentResponse(value)).orElse(null);
    }

    public List<CommentResponseTo> getComments() {
        return commentListMapper.toCommentResponseList(commentDao.findAll());
    }

    public CommentResponseTo saveComment(CommentRequestTo comment){
        Comment commentToSave = commentMapper.commentRequestToComment(comment);
        return commentMapper.commentToCommentResponse(commentDao.save(commentToSave));
    }

    public void deleteComment(Long id){
        commentDao.delete(id);
    }

    public CommentResponseTo updateComment(CommentRequestTo comment){
        Comment commentToUpdate = commentMapper.commentRequestToComment(comment);
        return commentMapper.commentToCommentResponse(commentDao.update(commentToUpdate));
    }
}
