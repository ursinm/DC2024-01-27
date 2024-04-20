package by.denisova.jpa.facade;

import by.denisova.jpa.dto.request.CreateCommentDto;
import by.denisova.jpa.dto.request.UpdateCommentDto;
import by.denisova.jpa.dto.response.CommentResponseDto;
import by.denisova.jpa.mapper.CommentMapper;
import by.denisova.jpa.model.Comment;
import by.denisova.jpa.model.Story;
import by.denisova.jpa.service.CommentService;
import by.denisova.jpa.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentService commentService;
    private final StoryService storyService;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    public CommentResponseDto findById(Long id) {
        Comment comment = commentService.findById(id);
        return commentMapper.toCommentResponse(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll() {
        List<Comment> comments = commentService.findAll();

        return comments.stream().map(commentMapper::toCommentResponse).toList();
    }

    @Transactional
    public CommentResponseDto save(CreateCommentDto commentRequest) {
        Comment comment = commentMapper.toComment(commentRequest);

        Story story = storyService.findById(commentRequest.getStoryId());
        comment.setStory(story);

        Comment savedComment = commentService.save(comment);

        return commentMapper.toCommentResponse(savedComment);
    }

    @Transactional
    public CommentResponseDto update(UpdateCommentDto commentRequest) {
        Comment comment = commentService.findById(commentRequest.getId());

        Comment updatedComment = commentMapper.toComment(commentRequest, comment);

        if (commentRequest.getStoryId() != null) {
            Story story = storyService.findById(commentRequest.getStoryId());
            updatedComment.setStory(story);
        }

        return commentMapper.toCommentResponse(commentService.update(updatedComment));
    }

    @Transactional
    public void delete(Long id) {
        commentService.deleteById(id);
    }
}
