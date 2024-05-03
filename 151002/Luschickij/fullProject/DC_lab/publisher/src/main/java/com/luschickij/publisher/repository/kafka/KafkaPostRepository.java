package com.luschickij.publisher.repository.kafka;

import com.luschickij.publisher.dto.KafkaPostRequestTo;
import com.luschickij.publisher.dto.KafkaPostResponseTo;
import com.luschickij.publisher.controller.kafka.Sender;
import com.luschickij.publisher.model.Post;
import com.luschickij.publisher.model.RestError;
import com.luschickij.publisher.repository.PostRepository;
import com.luschickij.publisher.repository.exception.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeoutException;

import static com.luschickij.publisher.repository.kafka.KafkaPostType.*;

@Service
public class KafkaPostRepository implements PostRepository {

    Sender sender;

    String responseTopic;

    String requestTopic;

    public KafkaPostRepository(Sender sender,
                                  @Value("${kafka.topic.post.response}") String responseTopic,
                                  @Value("${kafka.topic.post.request}$") String requestTopic) {
        this.sender = sender;
        this.responseTopic = responseTopic;
        this.requestTopic = requestTopic;
    }

    @Override
    public List<Post> findAll() throws EntityNotFoundException {

        KafkaPostRequestTo kafkaPostRequestTo = KafkaPostRequestTo.builder()
                .method(GET_ALL.getValue())
                .build();

        KafkaPostResponseTo post = null;
        try {
            post = sender.sendAndReceive("post-request", kafkaPostRequestTo);
        } catch (TimeoutException e) {
            throw new RuntimeException("Response took too long");
        }
        handleException(post);
        return post.getPosts();
    }

    @Override
    public Post save(Post entity) throws EntityNotFoundException {
        entity.setId(getId());
        KafkaPostRequestTo kafkaPostRequestTo = KafkaPostRequestTo.builder()
                .method(SAVE.getValue())
                .body(entity)
                .build();

        sender.send("post-request", kafkaPostRequestTo);

        return entity;
    }

    @Override
    public void deleteById(Long aLong) throws EntityNotFoundException {
        KafkaPostRequestTo kafkaPostRequestTo = KafkaPostRequestTo.builder()
                .method(DELETE.getValue())
                .params(Map.of("id", aLong.toString()))
                .build();

        KafkaPostResponseTo post = null;
        try {
            post = sender.sendAndReceive("post-request", kafkaPostRequestTo);
        } catch (TimeoutException e) {
            throw new RuntimeException("Response took too long");
        }

        handleException(post);
    }

    @Override
    public Optional<Post> findById(Long aLong) throws EntityNotFoundException {

        KafkaPostRequestTo kafkaPostRequestTo = KafkaPostRequestTo.builder()
                .method(ONE.getValue())
                .params(Map.of("id", aLong.toString()))
                .build();

        KafkaPostResponseTo post = null;
        try {
            post = sender.sendAndReceive("post-request", kafkaPostRequestTo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (post.getStatus().equals("error") && post.getError().getStatus().equals(HttpStatus.NOT_FOUND)) {
            return Optional.empty();
        }

        handleException(post);
        return Optional.ofNullable(post.getPosts().get(0));
    }

    @Override
    public Post update(Post post) {

        KafkaPostRequestTo kafkaPostRequestTo = KafkaPostRequestTo.builder()
                .method(UPDATE.getValue())
                .body(post)
                .build();

        KafkaPostResponseTo kafkaPostResponseTo = null;
        try {
            kafkaPostResponseTo = sender.sendAndReceive("post-request", kafkaPostRequestTo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        handleException(kafkaPostResponseTo);
        return kafkaPostResponseTo.getPosts().get(0);
    }

    private void handleException(KafkaPostResponseTo response) {
        if (response.getStatus().equals("error")) {
            RestError error = response.getError();
            if (error == null || error.getStatus() == null) {
                throw new RuntimeException("Unknown error. Error not specified");
            }

            if (error.getStatus().equals(HttpStatus.BAD_REQUEST)) {
                throw new ValidationException(error.getPost());
            }

            if (error.getStatus().equals(HttpStatus.NOT_FOUND)) {
                throw new EntityNotFoundException(error.getPost());
            }
        }
    }

    private Long getId() {
        int currentSecond = (int) (System.currentTimeMillis() / 1000);

        int shiftedTime = currentSecond << 10;

        int randomBits = new Random().nextInt(1 << 10);

        return (long) Math.abs(shiftedTime | randomBits);
    }
}
