package com.example.lab2.Service;

import com.example.lab2.DTO.CommentRequestTo;
import com.example.lab2.DTO.CommentResponseTo;
import com.example.lab2.Exception.NotFoundException;
import com.example.lab2.Mapper.CommentListMapper;
import com.example.lab2.Mapper.CommentMapper;
import com.example.lab2.Model.Comment;
import com.example.lab2.Repository.StoryRepository;
import com.example.lab2.Repository.CommentRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class CommentService {
    @Autowired
    //CommentDao commentDao;
    CommentRepository commentRepository;
    @Autowired
    StoryRepository storyRepository;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentListMapper commentListMapper;

    public CommentResponseTo create(@Valid CommentRequestTo commentRequestTo){
        Comment comment = commentMapper.commentRequestToComment(commentRequestTo);
        if(commentRequestTo.getStoryId() != 0){
            comment.setStory(storyRepository.findById(commentRequestTo.getStoryId()).orElseThrow(() -> new NotFoundException("Story not found", 404)));
        }
        return commentMapper.commentToCommentResponse(commentRepository.save(comment));
    }

    public List<CommentResponseTo> readAll(int pageInd, int numOfElem, String sortedBy, String direction){
        Pageable p;
        if(direction != null && direction.equals("asc"))
            p = PageRequest.of(pageInd, numOfElem, Sort.by(sortedBy).ascending());
        else
            p = PageRequest.of(pageInd, numOfElem, Sort.by(sortedBy).descending());

        Page<Comment> res = commentRepository.findAll(p);
        return commentListMapper.toCommentResponseList(res.toList());
    }

    public CommentResponseTo read(@Min(0) int id) throws NotFoundException {
        if(commentRepository.existsById(id)){
            CommentResponseTo commentResponseTo = commentMapper.commentToCommentResponse(commentRepository.getReferenceById(id));
            return commentResponseTo;
        }
        else
            throw new NotFoundException("Comment not found", 404);
    }
    public CommentResponseTo update(@Valid CommentRequestTo commentRequestTo, @Min(0) int id) throws NotFoundException {
        if(commentRepository.existsById(id)){
            Comment comment = commentMapper.commentRequestToComment(commentRequestTo);
            comment.setId(id);
            if(commentRequestTo.getStoryId() != 0){
                comment.setStory(storyRepository.findById(commentRequestTo.getStoryId()).orElseThrow(() -> new NotFoundException("Story not found", 404)));
            }
            return commentMapper.commentToCommentResponse(commentRepository.save(comment));
        }
        else
            throw new NotFoundException("Comment not found", 404);
    }
    public boolean delete(@Min(0) int id) throws NotFoundException {
        if(commentRepository.existsById(id)){
            commentRepository.deleteById(id);
            return true;
        }
        else
            throw new NotFoundException("Comment not found", 404);
    }

}
