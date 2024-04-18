package by.denisova.jpa.service.impl;

import by.denisova.jpa.exception.EntityNotFoundException;
import by.denisova.jpa.model.Comment;
import by.denisova.jpa.repository.impl.CommentRepository;
import by.denisova.jpa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    public static final String NOTE_NOT_FOUND_MESSAGE = "Comment with id '%d' doesn't exist";
    private final CommentRepository commentRepository;

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            final String message = NOTE_NOT_FOUND_MESSAGE.formatted(id);
            return new EntityNotFoundException(message);
        });
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE));

        commentRepository.deleteById(id);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        commentRepository.findById(comment.getId()).orElseThrow(()-> new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE));

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }
}

