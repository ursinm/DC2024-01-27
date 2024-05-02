package com.luschickij.discussion.kafkacontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luschickij.discussion.dto.post.PostRequestTo;
import com.luschickij.discussion.dto.post.PostResponseTo;
import com.luschickij.discussion.model.KafkaPostRequestTo;
import com.luschickij.discussion.model.KafkaPostResponseTo;
import com.luschickij.discussion.model.RestError;
import com.luschickij.discussion.repository.exception.EntityNotFoundException;
import com.luschickij.discussion.service.PostService;
import com.luschickij.discussion.service.kafka.KafkaPostType;
import com.luschickij.discussion.service.kafka.KafkaSendReceiverMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class Listener {

    ObjectMapper mapper;

    KafkaSendReceiverMap<UUID> sendReceiverMap;

    PostService postService;

    Sender sender;

    String responseTopic;

    @Autowired
    public Listener(ObjectMapper mapper,
                    KafkaSendReceiverMap<UUID> sendReceiverMap,
                    PostService postService,
                    Sender sender,
                    @Value("${kafka.topic.post.response}") String responseTopic) {
        this.mapper = mapper;
        this.sendReceiverMap = sendReceiverMap;
        this.postService = postService;
        this.sender = sender;
        this.responseTopic = responseTopic;
    }

    @KafkaListener(topics = "${kafka.topic.post.request}")
    public void postListener(KafkaPostRequestTo request) {

        log.info("Post received: {}", request);
        KafkaPostResponseTo response = new KafkaPostResponseTo();
        UUID requestId = request.getId();

        try {

            response.setRequestId(requestId);
            String method = request.getMethod();
            KafkaPostType kafkaPostType = KafkaPostType.valueOf(method);
            switch (kafkaPostType) {
                case GET_ALL -> {
                    getAll(response);
                    sender.send("post-response", response);
                }
                case ONE -> {
                    getOne(request, response);
                    sender.send("post-response", response);
                }
                case SAVE -> {
                    try {
                        create(request);
                    }catch (Exception e){

                    }

                }
                case UPDATE -> {
                    update(request, response);
                    sender.send("post-response", response);
                }
                case DELETE -> {
                    delete(request, response);
                    sender.send("post-response", response);
                }
                default -> {
                    RestError error = RestError.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .post("Unknown method")
                            .build();
                    response.setError(error);
                    sender.send("post-response", response);
                }
            }
        } catch (Exception e) {
            if (response.getStatus() == null || response.getError() == null) {
                response.setStatus("error");
                response.setError(RestError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).post(e.getMessage()).build());
            }
            sender.send("post-response", response);
        }
    }

    private void getAll(KafkaPostResponseTo response) {
        List<PostResponseTo> responseToList = postService.all();

        response.setStatus("ok");
        response.setPosts(responseToList);
    }

    private void getOne(KafkaPostRequestTo request, KafkaPostResponseTo response) {
        String idString = request.getParams().get("id");

        if (idString == null) {
            RestError error = RestError.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .post("id is null")
                    .build();
            log.info("id is null");
            response.setStatus("error");
            response.setError(error);
            return;
        }
        Long id = Long.valueOf(idString);

        Optional<PostResponseTo> post = postService.one(id);

        if (post.isEmpty()) {
            RestError error = RestError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .post("post not found")
                    .build();

            log.info("post not found");
            response.setError(error);
            response.setStatus("error");
            return;
        }
        //set response post
        response.setStatus("ok");
        response.setPosts(List.of(post.get()));
    }

    private void create(KafkaPostRequestTo request) {
        PostRequestTo requestBody = request.getBody();

        postService.create(requestBody);
    }

    private void update(KafkaPostRequestTo request, KafkaPostResponseTo response) {
        PostRequestTo requestBody = request.getBody();

        if (requestBody == null || requestBody.getId() == null) {
            postNotFound(requestBody, response);
            return;
        }

        Optional<PostResponseTo> responseTo = postService.update(requestBody.getId(), requestBody);

        if (responseTo.isEmpty()) {
            log.info("post not found");
            response.setStatus("error");
            RestError error = RestError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .post("post not found")
                    .build();
            response.setError(error);
            return;
        }
        response.setStatus("ok");
        response.setPosts(List.of(responseTo.get()));
    }

    private void delete(KafkaPostRequestTo request, KafkaPostResponseTo response) {
        String idString = request.getParams().get("id");
        Long id = Long.valueOf(idString);
        try {
            postService.delete(id);
            response.setStatus("ok");
        } catch (EntityNotFoundException e) {
            response.setStatus("error");
            RestError error = RestError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .post(e.getMessage())
                    .build();
            response.setError(error);
        }
    }


    private void postNotFound(PostRequestTo requestBody, KafkaPostResponseTo response) {
        log.info("post not found");
        response.setStatus("error");
        RestError error = RestError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .post("post not found")
                .build();
        response.setError(error);
    }

}
