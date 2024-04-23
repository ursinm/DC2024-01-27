package by.denisova.rest.facade;

import by.denisova.rest.mapper.CommentMapper;
import by.denisova.rest.service.CommentService;
import by.denisova.rest.dto.request.CreateCommentDto;
import by.denisova.rest.dto.request.UpdateCommentDto;
import by.denisova.rest.dto.response.CommentResponseDto;
import by.denisova.rest.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentResponseDto findById(Long id) {
        Comment comment = commentService.findById(id);
        return commentMapper.toCommentResponse(comment);
    }

    public List<CommentResponseDto> findAll() {
        List<Comment> comments = commentService.findAll();

        return comments.stream().map(commentMapper::toCommentResponse).toList();
    }

    public CommentResponseDto save(CreateCommentDto commentRequest) {
        Comment comment = commentMapper.toComment(commentRequest);

        Comment savedComment = commentService.save(comment);

        return commentMapper.toCommentResponse(savedComment);
    }

    public CommentResponseDto update(UpdateCommentDto commentRequest) {
        Comment comment = commentService.findById(commentRequest.getId());

        Comment updatedComment = commentMapper.toComment(commentRequest, comment);

        return commentMapper.toCommentResponse(commentService.update(updatedComment));
    }

    public void delete(Long id) {
        commentService.deleteById(id);
    }
}
