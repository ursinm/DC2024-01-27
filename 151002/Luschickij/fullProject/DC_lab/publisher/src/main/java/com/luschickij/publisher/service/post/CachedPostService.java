package com.luschickij.publisher.service.post;

import com.luschickij.publisher.dto.post.PostRequestTo;
import com.luschickij.publisher.dto.post.PostResponseTo;
import com.luschickij.publisher.model.Post;
import com.luschickij.publisher.repository.PostRepository;
import com.luschickij.publisher.repository.exception.EntityNotFoundException;
import com.luschickij.publisher.service.redis.RedisCacheService;
import com.luschickij.publisher.utils.dtoconverter.PostRequestDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CachedPostService extends PostService {

    RedisCacheService<Long, PostResponseTo> redisCacheService;
    ExecutorService executorService;

    @Autowired
    public CachedPostService(
            @Qualifier("kafkaPostRepository") PostRepository repository,
            PostRequestDtoConverter postRequestDtoConverter,
            RedisCacheService<Long, PostResponseTo> redisCacheService,
            @Value("${redis.threads.count}") Integer threadsCount
    ) {
        super(repository, postRequestDtoConverter);
        this.redisCacheService = redisCacheService;
        this.executorService = Executors.newFixedThreadPool(threadsCount);
    }

    @Override
    public Optional<PostResponseTo> update(Long aLong, PostRequestTo postRequestTo) {

        if (redisCacheService.get(aLong) != null) {
            PostResponseTo updated = convert(postRequestTo);
            redisCacheService.put(aLong, updated);
            executorService.execute(() -> {
                try{
                    super.update(aLong,postRequestTo);
                }catch (Exception e){
                    redisCacheService.delete(aLong);
                }
            });

            return Optional.of(updated);
        }

        Optional<PostResponseTo> postResponseTo = super.update(aLong, postRequestTo);
        postResponseTo.ifPresent(responseTo -> redisCacheService.put(aLong, responseTo));
        return postResponseTo;
    }

    @Override
    public void delete(Long aLong) throws EntityNotFoundException {
        redisCacheService.delete(aLong);
        super.delete(aLong);
    }

    @Override
    public Optional<PostResponseTo> one(Long aLong) {
        PostResponseTo postResponseTo = redisCacheService.get(aLong);
        if (postResponseTo != null) {
            return Optional.of(postResponseTo);
        }

        Optional<PostResponseTo> response = super.one(aLong);
        response.ifPresent(postResponseTo1 -> redisCacheService.put(aLong, postResponseTo1));
        return response;
    }

    @Override
    public Optional<PostResponseTo> create(PostRequestTo postRequestTo) {
        Optional<PostResponseTo> postResponseTo = super.create(postRequestTo);
        postResponseTo.ifPresent(responseTo -> redisCacheService.put(responseTo.getId(), responseTo));

        return postResponseTo;
    }

    private PostResponseTo convert(PostRequestTo postRequestTo) {
        return PostResponseTo.builder()
                .id(postRequestTo.getId())
                .content(postRequestTo.getContent())
                .newsId(postRequestTo.getNewsId())
                .build();
    }
}
