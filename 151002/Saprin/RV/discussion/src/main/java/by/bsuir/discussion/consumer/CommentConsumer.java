package by.bsuir.discussion.consumer;

import by.bsuir.discussion.bean.Comment;
import by.bsuir.discussion.dto.CommentRequestTo;
import by.bsuir.discussion.dto.CommentResponseTo;
import by.bsuir.discussion.exception.DuplicateEntityException;
import by.bsuir.discussion.exception.EntityNotFoundException;
import by.bsuir.discussion.producer.CommentProducer;
import by.bsuir.discussion.service.comment.impl.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class CommentConsumer {

    private final CommentProducer commentProducer;
    private final CommentService commentService;

    @Autowired
    public CommentConsumer(CommentProducer commentProducer, CommentService commentService) {
        this.commentProducer = commentProducer;
        this.commentService = commentService;
    }

    @KafkaListener(topics = "OutTopic", groupId = "my-group")
    public void consume(String message) throws JsonProcessingException, DuplicateEntityException, EntityNotFoundException {

        if (message.startsWith("GET:")) {
            String commentId = message.substring(4);
            CommentResponseTo commentResponseTo = getCommentResponseTo(new BigInteger(commentId));
            commentProducer.sendMessage(commentResponseTo);
        } if (message.startsWith("POST:")) {
            String commentJson = message.substring(5);
            CommentRequestTo comment = parseToCommentRequestTo(commentJson);
            CommentResponseTo commentResponseTo = commentService.addComment(comment);
            commentProducer.sendMessage(commentResponseTo);
        } if (message.startsWith("DELETE:")) {
            String commentId = message.substring(7);
            commentService.deleteComment(new BigInteger(commentId));
            commentProducer.sendMessage(new CommentResponseTo());
        } if (message.startsWith("PUT:")) {
            String commentJson = message.substring(4);
            CommentRequestTo comment = parseToCommentRequestTo(commentJson);
            CommentResponseTo commentResponseTo = commentService.updateComment(comment);
            commentProducer.sendMessage(commentResponseTo);
        }
    }

    private CommentRequestTo parseToCommentRequestTo(String comment) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(comment, CommentRequestTo.class);

    }

    private CommentResponseTo getCommentResponseTo(BigInteger id) throws EntityNotFoundException {
        return commentService.getCommentById(id);
    }
}

