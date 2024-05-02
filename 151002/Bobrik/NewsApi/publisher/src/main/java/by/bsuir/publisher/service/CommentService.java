package by.bsuir.publisher.service;

import by.bsuir.publisher.model.request.CommentRequestTo;
import by.bsuir.publisher.model.response.CommentResponseTo;
import by.bsuir.publisher.event.*;
import by.bsuir.publisher.service.exceptions.CommentExchangeFailedException;
import by.bsuir.publisher.service.exceptions.ResourceNotFoundException;
import by.bsuir.publisher.model.entity.CommentState;
import by.bsuir.publisher.dao.NewsRepository;
import by.bsuir.publisher.service.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;
    private final KafkaCommentClient KafkaCommentClient;
    private static final SecureRandom random;
    private final NewsRepository NewsRepository;

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

    public List<CommentResponseTo> getAll() {
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.GET_ALL);
        OutTopicMessage outTopicMsg = KafkaCommentClient.sync(inTopicMsg);
        return commentMapper.toDto(outTopicMsg.resultList());
    }

    public void deleteById(Long id) {
        CommentInTopicTo inTopicDto = new CommentInTopicTo();
        inTopicDto.setId(id);
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.DELETE_BY_ID, inTopicDto);
        OutTopicMessage outTopicMsg = KafkaCommentClient.sync(inTopicMsg);
        CommentOutTopicTo deleted = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("comment with id = " + id + " is not found", 40489));
        if (!Objects.equals(id, deleted.id())) {
            throw new CommentExchangeFailedException("Note request return invalid id: expected = " + id + ", returned = " + deleted.id());
        }
    }

    public CommentResponseTo create(CommentRequestTo dto, Locale locale) {
        Long newsId = dto.newsId();
        if (!NewsRepository.existsById(newsId)) {
            throw new ResourceNotFoundException("news with id = " + newsId + " doesn't exist", 40490);
        }
        CommentInTopicTo inTopicDto = commentMapper.toInTopicDto(dto);
        inTopicDto.setId(getTimeBasedId());
        inTopicDto.setCountry(locale);
        inTopicDto.setState(CommentState.PENDING);
        InTopicMessage inTopicMsg = new InTopicMessage(
                InTopicMessage.Operation.CREATE, inTopicDto);
        System.err.println("\n\n" + inTopicMsg.operation() + "  " + inTopicMsg.commentDto().getContent()
                + "  " + inTopicMsg.commentDto().getState() + "  " + inTopicMsg.commentDto().getCountry() + "  " +
                inTopicMsg.commentDto().getId()  + "\n\n");
        OutTopicMessage outTopicMsg = KafkaCommentClient.sync(inTopicMsg);
        CommentOutTopicTo created = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new CommentExchangeFailedException("Getting note with id = "
                        + inTopicDto.getId() + " failed"));
        return commentMapper.toDto(created);
    }

    public CommentResponseTo getById(Long id) {
        CommentInTopicTo inTopicDto = new CommentInTopicTo();
        inTopicDto.setId(id);
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.GET_BY_ID, inTopicDto);
        OutTopicMessage outTopicMsg = KafkaCommentClient.sync(inTopicMsg);
        CommentOutTopicTo noteOut = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id = " + id + " is not found", 40411));
        return commentMapper.toDto(noteOut);
    }

    public CommentResponseTo update(CommentRequestTo dto) {
        Long newsId = dto.newsId();
        if (!NewsRepository.existsById(newsId)) {
            throw new ResourceNotFoundException("news with id = " + newsId + " doesn't exist", 40424);
        }
        CommentInTopicTo inTopicDto = commentMapper.toInTopicDto(dto);
        inTopicDto.setState(CommentState.PENDING);
        InTopicMessage inTopicMsg = new InTopicMessage(
                InTopicMessage.Operation.UPDATE, inTopicDto);
        OutTopicMessage outTopicMsg = KafkaCommentClient.sync(inTopicMsg);
        CommentOutTopicTo updated = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new CommentExchangeFailedException("Getting note with id = "
                        + inTopicDto.getId() + " failed"));
        return commentMapper.toDto(updated);
    }
}