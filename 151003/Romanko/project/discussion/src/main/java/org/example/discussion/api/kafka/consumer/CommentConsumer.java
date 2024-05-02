package org.example.discussion.api.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.discussion.api.exception.DuplicateEntityException;
import org.example.discussion.api.exception.EntityNotFoundException;
import org.example.discussion.api.kafka.producer.CommentProducer;
import org.example.discussion.impl.comment.dto.CommentRequestTo;
import org.example.discussion.impl.comment.dto.CommentResponseTo;
import org.example.discussion.impl.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class CommentConsumer {
    private final CommentService commentService;
    private final CommentProducer commentProducer;
    private final String INPUT_TOPIC = "OutTopic";

    @Autowired
    public CommentConsumer(CommentService commentService, CommentProducer commentProducer) {
        this.commentService = commentService;
        this.commentProducer = commentProducer;
    }

    @KafkaListener(topics = INPUT_TOPIC, groupId = "group")
    public void listen(String message) throws EntityNotFoundException, JsonProcessingException, DuplicateEntityException {
        String requestId = message.substring(0, message.indexOf(","));
        if (message.contains("get:")) {
            String id = message.substring(message.indexOf(":") + 1);
            CommentResponseTo comment = commentService.getCommentById(new BigInteger(id));
            commentProducer.sendResponseComment(comment, requestId);
        } else if (message.contains("post:")) {
            String commentString = message.substring(message.indexOf(":") + 1);
            commentService.saveComment(convertJsonToCommentRequestTo(commentString));
        } else if (message.contains("put:")) {
            String commentString = message.substring(message.indexOf(":") + 1);
            CommentResponseTo comment = commentService.updateComment(convertJsonToCommentRequestTo(commentString));
            commentProducer.sendResponseComment(comment, requestId);
        } else if (message.contains("delete:")) {
            String id = message.substring(message.indexOf(":") + 1);
            commentService.deleteComment(new BigInteger(id));
        }
    }

    private CommentRequestTo convertJsonToCommentRequestTo(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, CommentRequestTo.class);
    }
}
