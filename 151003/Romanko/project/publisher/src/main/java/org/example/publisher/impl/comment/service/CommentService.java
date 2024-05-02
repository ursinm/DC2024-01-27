package org.example.publisher.impl.comment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.publisher.api.exception.DuplicateEntityException;
import org.example.publisher.api.exception.EntityNotFoundException;
import org.example.publisher.api.kafka.producer.CommentProducer;
import org.example.publisher.impl.comment.dto.CommentAddedResponseTo;
import org.example.publisher.impl.comment.dto.CommentRequestTo;
import org.example.publisher.impl.comment.dto.CommentResponseTo;
import org.example.publisher.impl.comment.mapper.Impl.CommentMapperImpl;
import org.example.publisher.impl.story.Story;
import org.example.publisher.impl.story.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "commentCache")
public class CommentService {
    private final StoryRepository storyRepository;

    private final CommentMapperImpl commentMapper;
    private final CommentProducer commentProducer;

    private final String ENTITY_NAME = "comment";

    private final String NOTE_COMMENT = "http://localhost:24130/api/v1.0/comments";

    @Cacheable(cacheNames = "comment")
    public List<CommentResponseTo> getComments(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CommentResponseTo[]> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommentResponseTo[]> response = restTemplate.exchange(NOTE_COMMENT, HttpMethod.GET, requestEntity, CommentResponseTo[].class);
        return new ArrayList<>(List.of(Objects.requireNonNull(response.getBody())));
    }

    @Cacheable(cacheNames = "comment", key = "#id", unless = "#result == null")
    public CommentResponseTo getCommentById(BigInteger id) throws EntityNotFoundException, InterruptedException {
        try
        {
            return commentProducer.sendComment("get", id.toString(), true);
        } catch (RuntimeException e)
        {
            throw new EntityNotFoundException("comment", id);
        }

    }

    @CacheEvict(cacheNames = "comment", allEntries = true)
    public CommentAddedResponseTo saveComment(CommentRequestTo commentTO) throws EntityNotFoundException, DuplicateEntityException, InterruptedException {
        Optional<Story> story = storyRepository.findById(commentTO.getStoryId());
        if (story.isEmpty()){
            throw new EntityNotFoundException("story", commentTO.getStoryId());
        }
        try {
            commentTO.setId(generateId(16));
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(commentTO);
            commentProducer.sendComment("post", json, false);
            return new CommentAddedResponseTo(commentTO.getId(), commentTO.getStoryId(), commentTO.getContent(), "pending");
        } catch (DataIntegrityViolationException | JsonProcessingException e){
            throw new DuplicateEntityException(ENTITY_NAME, "");
        }
    }

    @CacheEvict(cacheNames = "comment", allEntries = true)
    public CommentResponseTo updateComment(CommentRequestTo commentTO) throws EntityNotFoundException, DuplicateEntityException, JsonProcessingException, InterruptedException {
        Optional<Story> story = storyRepository.findById(commentTO.getStoryId());
        if (story.isEmpty()){
            throw new EntityNotFoundException("story", commentTO.getStoryId());
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(commentTO);
            CommentResponseTo comment = commentProducer.sendComment("put", json, true);
            return comment;
        }catch (DataIntegrityViolationException e){
            throw new DuplicateEntityException(ENTITY_NAME, "");
        }
    }

    @Caching(evict = { @CacheEvict(cacheNames = "comment", key = "#id"),
            @CacheEvict(cacheNames = "comment", allEntries = true) })
    public void deleteComment(BigInteger id) throws EntityNotFoundException, InterruptedException {
        try
        {
            commentProducer.sendComment("get", id.toString(), true);
        }catch (RuntimeException e)
        {
            throw new EntityNotFoundException("comment", id);
        }
        commentProducer.sendComment("delete", id.toString(), false);
    }

    public static BigInteger generateId(int numBits) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[numBits / 8];
        secureRandom.nextBytes(bytes);
        return new BigInteger(1, bytes);
    }
}
