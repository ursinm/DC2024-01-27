package org.example.discussion.api.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.discussion.api.exception.DuplicateEntityException;
import org.example.discussion.api.exception.EntityNotFoundException;
import org.example.discussion.api.kafka.producer.PostProducer;
import org.example.discussion.impl.post.dto.PostRequestTo;
import org.example.discussion.impl.post.dto.PostResponseTo;
import org.example.discussion.impl.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class PostConsumer {
    private final PostService postService;
    private final PostProducer postProducer;
    private final String INPUT_TOPIC = "OutTopic";

    @Autowired
    public PostConsumer(PostService postService, PostProducer postProducer) {
        this.postService = postService;
        this.postProducer = postProducer;
    }

    @KafkaListener(topics = INPUT_TOPIC, groupId = "group")
    public void listen(String message) throws EntityNotFoundException, JsonProcessingException, DuplicateEntityException {
        String requestId = message.substring(0, message.indexOf(","));
        if (message.contains("get:")) {
            String id = message.substring(message.indexOf(":") + 1);
            PostResponseTo post = postService.getPostById(new BigInteger(id));
            postProducer.sendResponsePost(post, requestId);
        } else if (message.contains("post:")) {
            String noteString = message.substring(message.indexOf(":") + 1);
            postService.savePost(convertJsonToPostRequestTo(noteString));
        } else if (message.contains("put:")) {
            String postString = message.substring(message.indexOf(":") + 1);
            PostResponseTo post = postService.updatePost(convertJsonToPostRequestTo(postString));
            postProducer.sendResponsePost(post, requestId);
        } else if (message.contains("delete:")) {
            String id = message.substring(message.indexOf(":") + 1);
            postService.deletePost(new BigInteger(id));
        }
    }

    private PostRequestTo convertJsonToPostRequestTo(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, PostRequestTo.class);
    }
}
