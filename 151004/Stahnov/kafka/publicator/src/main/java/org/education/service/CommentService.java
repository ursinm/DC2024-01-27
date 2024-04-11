package org.education.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.education.bean.Comment;
import org.education.bean.DTO.CommentMessageRequest;
import org.education.bean.DTO.CommentMessageResponse;
import org.education.bean.DTO.CommentRequestTo;
import org.education.exception.AlreadyExists;
import org.education.exception.IncorrectValuesException;
import org.education.exception.NoSuchComment;
import org.education.exception.NoSuchTweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@CacheConfig(cacheNames = "commentCache")
public class CommentService {

    private static final AtomicInteger index = new AtomicInteger(0);
    private static final AtomicInteger messageIndex = new AtomicInteger(0);
    private final ObjectMapper objectMapper;
    private final TweetService tweetService;

    @Autowired
    private KafkaTemplate<String, CommentMessageRequest> commentKafkaTemplate;

    @Value("${kafka.query-topic}")
    private String queryTopic;

    @Value("${kafka.result-topic}")
    private String resultTopic;

    public CommentService(ObjectMapper objectMapper, TweetService tweetService) {
        this.objectMapper = objectMapper;
        this.tweetService = tweetService;
    }

    ConcurrentHashMap<Integer, CompletableFuture<CommentMessageResponse>> taskMap = new ConcurrentHashMap<>();
    @KafkaListener(topics = "${kafka.result-topic}", groupId = "publicator-group",
            containerFactory = "userKafkaListenerContainerFactory")
    void listenerWithMessageConverter(CommentMessageResponse commentMessage) {
        CompletableFuture<CommentMessageResponse> task = taskMap.getOrDefault(commentMessage.getIndex(), null);
        if(task != null){
            task.complete(commentMessage);
        }
        taskMap.remove(commentMessage.getIndex());
    }

    @Cacheable(cacheNames = "comments")
    public List<Comment> getAll(){
        int ind = messageIndex.decrementAndGet();
        CompletableFuture<CommentMessageResponse> resultTask = new CompletableFuture<>();
        commentKafkaTemplate.send(queryTopic, "get", new CommentMessageRequest("GET",ind , new ArrayList<>()));
        taskMap.put(ind, resultTask);
        try {
            CommentMessageResponse res = resultTask.get(1000, TimeUnit.MILLISECONDS);
            if (res.getMessage().equals("SUCCESS")){
                return res.getComments();
            }
            else{
                throw new RuntimeException(res.getMessage());
            }
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
                throw new RuntimeException(e.getMessage());
        }
    }

    @Cacheable(cacheNames = "comments", key = "#id", unless = "#result == null")
    public Comment getById(int id){

        CommentRequestTo temp = new CommentRequestTo();
        temp.setId(id);

        int ind = messageIndex.incrementAndGet();
        CompletableFuture<CommentMessageResponse> resultTask = new CompletableFuture<>();
        commentKafkaTemplate.send(queryTopic, "get", new CommentMessageRequest("GET",ind ,List.of(temp)));
        taskMap.put(ind, resultTask);
        try {
            CommentMessageResponse res = resultTask.get(1000, TimeUnit.SECONDS);
            if (res.getMessage().equals("SUCCESS")){
                return res.getComments().get(0);
            }
            else{
                if(res.getMessage().equals("NO SUCH COMMENT")){
                    throw new NoSuchComment(res.getMessage());
                }
                throw new RuntimeException(res.getMessage());
            }
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @CacheEvict(cacheNames = "comments", allEntries = true)
    public Comment create(CommentRequestTo comment){
        if(!tweetService.existsWithId(comment.getTweetId())) throw new NoSuchTweet("No such tweet with this id");
        comment.setId(index.incrementAndGet());

        int ind = messageIndex.incrementAndGet();
        CompletableFuture<CommentMessageResponse> resultTask = new CompletableFuture<>();
        taskMap.put(ind, resultTask);
        commentKafkaTemplate.send(queryTopic, String.valueOf(comment.getId()), new CommentMessageRequest("CREATE", ind,List.of(comment)));
        try {
            CommentMessageResponse res = resultTask.get(1000, TimeUnit.SECONDS);
            if (res.getMessage().equals("SUCCESS")){
                return res.getComments().get(0);
            }
            else{
                if(res.getMessage().equals("ALREADY EXISTS")){
                    throw new AlreadyExists(res.getMessage());
                }
                throw new RuntimeException(res.getMessage());
            }
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @CacheEvict(cacheNames = "comments", allEntries = true)
    public Comment update(CommentRequestTo comment){
        int ind = messageIndex.incrementAndGet();
        CompletableFuture<CommentMessageResponse> resultTask = new CompletableFuture<>();
        taskMap.put(ind, resultTask);
        commentKafkaTemplate.send(queryTopic, String.valueOf(comment.getId()), new CommentMessageRequest("PUT", ind,List.of(comment)));
        try {
            CommentMessageResponse res = resultTask.get(1000, TimeUnit.SECONDS);
            if (res.getMessage().equals("SUCCESS")){
                return res.getComments().get(0);
            }
            else{
                if(res.getMessage().equals("NO SUCH COMMENT")){
                    throw new NoSuchComment(res.getMessage());
                }
                throw new RuntimeException(res.getMessage());
            }
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Caching(evict = { @CacheEvict(cacheNames = "comments", key = "#id"),
            @CacheEvict(cacheNames = "comments", allEntries = true) })
    public void delete(int id){
        CommentRequestTo temp = new CommentRequestTo();
        temp.setId(id);
        int ind = messageIndex.incrementAndGet();
        CompletableFuture<CommentMessageResponse> resultTask = new CompletableFuture<>();
        taskMap.put(ind, resultTask);
        commentKafkaTemplate.send(queryTopic, "delete", new CommentMessageRequest("DELETE",ind,List.of(temp)));
        try {
            CommentMessageResponse res = resultTask.get(1000, TimeUnit.SECONDS);
            if (!res.getMessage().equals("SUCCESS")){
                if(res.getMessage().equals("NO SUCH COMMENT")){
                    throw new NoSuchComment(res.getMessage());
                }
                throw new RuntimeException(res.getMessage());
            }
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
