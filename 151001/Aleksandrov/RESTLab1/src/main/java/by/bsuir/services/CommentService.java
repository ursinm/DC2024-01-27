package by.bsuir.services;

import by.bsuir.dto.CommentRequestTo;
import by.bsuir.dto.CommentResponseTo;
import by.bsuir.entities.Comment;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.CommentListMapper;
import by.bsuir.mapper.CommentMapper;
import by.bsuir.repository.CommentRepository;
import by.bsuir.repository.IssueRepository;
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
import java.util.Optional;

@Service
@Validated
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentRepository commentDao;
    @Autowired
    CommentListMapper commentListMapper;
    @Autowired
    IssueRepository issueRepository;

    public CommentResponseTo getCommentById(@Min(0) Long id) throws NotFoundException {
        Optional<Comment> comment = commentDao.findById(id);
        return comment.map(value -> commentMapper.commentToCommentResponse(value)).orElseThrow(() -> new NotFoundException("Comment not found!", 40004L));
    }

    public List<CommentResponseTo> getComments(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder!=null && sortOrder.equals("asc")){
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Comment> comments = commentDao.findAll(pageable);
        return commentListMapper.toCommentResponseList(comments.toList());
    }

    public CommentResponseTo saveComment(@Valid CommentRequestTo comment) {
        Comment commentToSave = commentMapper.commentRequestToComment(comment);
        if (comment.getIssueId()!=null) {
            commentToSave.setIssue(issueRepository.findById(comment.getIssueId()).orElseThrow(() -> new NotFoundException("Issue not found!", 40004L)));
        }
        return commentMapper.commentToCommentResponse(commentDao.save(commentToSave));
    }

    public void deleteComment(@Min(0) Long id) throws DeleteException {
        if (!commentDao.existsById(id)) {
            throw new DeleteException("Comment not found!", 40004L);
        } else {
            commentDao.deleteById(id);
        }
    }

    public CommentResponseTo updateComment(@Valid CommentRequestTo comment) throws UpdateException {
        Comment commentToUpdate = commentMapper.commentRequestToComment(comment);
        if (!commentDao.existsById(comment.getId())) {
            throw new UpdateException("Comment not found!", 40004L);
        } else {
            if (comment.getIssueId()!=null) {
                commentToUpdate.setIssue(issueRepository.findById(comment.getIssueId()).orElseThrow(() -> new NotFoundException("Issue not found!", 40004L)));
            }
            return commentMapper.commentToCommentResponse(commentDao.save(commentToUpdate));
        }
    }

    public List<CommentResponseTo> getCommentByIssueId(@Min(0) Long issueId) throws NotFoundException {
        List<Comment> comment = commentDao.findCommentsByIssue_Id(issueId);
        if (comment.isEmpty()){
            throw new NotFoundException("Comment not found!", 40004L);
        }
        return commentListMapper.toCommentResponseList(comment);
    }
}
