package org.example.publisher.impl.post.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.publisher.api.exception.DuplicateEntityException;
import org.example.publisher.api.exception.EntityNotFoundException;
import org.example.publisher.api.kafka.producer.PostProducer;
import org.example.publisher.impl.issue.Issue;
import org.example.publisher.impl.post.PostRepository;
import org.example.publisher.impl.post.dto.PostAddedResponseTo;
import org.example.publisher.impl.post.dto.PostRequestTo;
import org.example.publisher.impl.post.dto.PostResponseTo;
import org.example.publisher.impl.post.mapper.Impl.PostMapperImpl;
import org.example.publisher.impl.issue.IssueRepository;
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
@CacheConfig(cacheNames = "postsCache")
public class PostService {
    private final IssueRepository issueRepository;

    private final PostMapperImpl postMapper;
    private final PostProducer postProducer;
    private String currentId = "";
    private final String ENTITY_NAME = "post";

    private final String POST_COMMENT = "http://localhost:24130/api/v1.0/posts";

    @Cacheable(cacheNames = "posts")
    public List<PostResponseTo> getPosts(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PostResponseTo[]> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<PostResponseTo[]> response = restTemplate.exchange(POST_COMMENT, HttpMethod.GET, requestEntity, PostResponseTo[].class);
        return new ArrayList<>(List.of(Objects.requireNonNull(response.getBody())));
    }

    @Cacheable(cacheNames = "posts", key = "#id", unless = "#result == null")
    public PostResponseTo getPostById(BigInteger id) throws EntityNotFoundException, InterruptedException {
        return postProducer.sendPost("get", id.toString(), true);
    }

    @CacheEvict(cacheNames = "posts", allEntries = true)
    public PostAddedResponseTo savePost(PostRequestTo postTO) throws EntityNotFoundException, DuplicateEntityException, InterruptedException {
        Optional<Issue> issue = issueRepository.findById(postTO.getIssueId());
        if (issue.isEmpty()){
            throw new EntityNotFoundException("issue", postTO.getIssueId());
        }
        try {
            postTO.setId(generateId(16));
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(postTO);
            postProducer.sendPost("post", json, false);
            return new PostAddedResponseTo(postTO.getId(), postTO.getIssueId(), postTO.getContent(), "pending");
        } catch (DataIntegrityViolationException | JsonProcessingException e){
            throw new DuplicateEntityException(ENTITY_NAME, "");
        }
    }

    @CacheEvict(cacheNames = "posts", allEntries = true)
    public PostResponseTo updatePost(PostRequestTo postTO) throws EntityNotFoundException, DuplicateEntityException, JsonProcessingException, InterruptedException {
        Optional<Issue> tweet = issueRepository.findById(postTO.getIssueId());
        if (tweet.isEmpty()){
            throw new EntityNotFoundException("issue", postTO.getIssueId());
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(postTO);
            PostResponseTo post = postProducer.sendPost("put", json, true);
            return post;
        }catch (DataIntegrityViolationException e){
            throw new DuplicateEntityException(ENTITY_NAME, "");
        }
    }

    @Caching(evict = { @CacheEvict(cacheNames = "posts", key = "#id"),
            @CacheEvict(cacheNames = "posts", allEntries = true) })
    public void deletePost(BigInteger id) throws EntityNotFoundException, InterruptedException {
        if(currentId.equals(id.toString())){
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        else {
            postProducer.sendPost("delete", id.toString(), false);
            currentId = id.toString();
        }
    }

    public static BigInteger generateId(int numBits) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[numBits / 8];
        secureRandom.nextBytes(bytes);
        return new BigInteger(1, bytes);
    }
}
