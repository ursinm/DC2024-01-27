package by.bsuir.discussion.service;

import by.bsuir.discussion.dao.CommentRepository;
import by.bsuir.discussion.model.entity.Comment;
import by.bsuir.discussion.model.entity.CommentState;
import by.bsuir.discussion.model.request.CommentRequestTo;
import by.bsuir.discussion.model.response.CommentResponseTo;
import by.bsuir.discussion.service.exceptions.ResourceNotFoundException;
import by.bsuir.discussion.service.mapper.CommentMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CommentService implements RestService<CommentRequestTo, CommentResponseTo> {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private static final SecureRandom random;

    static {
        SecureRandom randomInstance;
        try {
            randomInstance = SecureRandom.getInstance("NativePRNG");
        } catch (NoSuchAlgorithmException ex) {
            randomInstance = new SecureRandom();
        }
        random = randomInstance;
    }

    private static Long getTimeBasedId(){
        return (((System.currentTimeMillis() << 16) | (random.nextLong() & 0xFFFF)));
    }

    @Override
    public List<CommentResponseTo> findAll() {
        return commentMapper.getListResponseTo(commentRepository.findAll());
    }

    @Override
    public CommentResponseTo findById(Long id) {
        return commentMapper.getResponseTo(commentRepository
                .findByKeyId(id)
                .orElseThrow(() -> commentNotFoundException(id)));
    }

    @Override
    public CommentResponseTo create(CommentRequestTo commentTo) {
        return create(commentTo, Locale.ENGLISH);
    }

    public CommentResponseTo create(@Valid CommentRequestTo commentTo, Locale locale) {
        Comment newComment = commentMapper.getComment(commentTo);
        newComment.getKey().setCountry(locale);
        newComment.getKey().setId(getTimeBasedId());
        newComment.setState(CommentState.APPROVE);
        return commentMapper.getResponseTo(commentRepository.save(newComment));
    }

    @Override
    public CommentResponseTo update(@Valid CommentRequestTo commentTo) {
        Comment comment = commentRepository
                .findByKeyId(commentTo.id())
                .orElseThrow(() -> commentNotFoundException(commentTo.id()));
        return commentMapper.getResponseTo(commentRepository.save(commentMapper.partialUpdate(commentTo, comment)));
    }

    @Override
    public void removeById(Long id) {
        Comment comment = commentRepository
                .findByKeyId(id)
                .orElseThrow(() -> commentNotFoundException(id));
        commentRepository.delete(comment);
    }

    private static ResourceNotFoundException commentNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find comment with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 83);
    }
}
