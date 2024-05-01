package by.denisova.rest.service.impl;

import by.denisova.rest.exception.EntityNotFoundException;
import by.denisova.rest.model.Comment;
import by.denisova.rest.repository.AbstractRepository;
import by.denisova.rest.service.CommentService;
import by.denisova.rest.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    public static final String COMMENT_NOT_FOUND_MESSAGE = "Comment with id '%d' doesn't exist";
    private final AbstractRepository<Comment, Long> commentRepository;
    private final StoryService storyService;

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            final String message = COMMENT_NOT_FOUND_MESSAGE.formatted(id);
            return new EntityNotFoundException(message);
        });
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(COMMENT_NOT_FOUND_MESSAGE));

        commentRepository.deleteById(id);
    }

    @Override
    public Comment save(Comment comment) {
        storyService.findById(comment.getStoryId());
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {storyService.findById(comment.getStoryId());
        commentRepository.findById(comment.getId()).orElseThrow(()-> new EntityNotFoundException(COMMENT_NOT_FOUND_MESSAGE));
        storyService.findById(comment.getStoryId());

        return commentRepository.update(comment);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }
}

