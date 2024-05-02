package com.luschickij.publisher.service.post;

import com.luschickij.publisher.dto.post.PostRequestTo;
import com.luschickij.publisher.dto.post.PostResponseTo;
import com.luschickij.publisher.model.Post;
import com.luschickij.publisher.repository.PostRepository;
import com.luschickij.publisher.service.CommonRestService;
import com.luschickij.publisher.utils.dtoconverter.PostRequestDtoConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostService extends CommonRestService<Post, PostRequestTo, PostResponseTo, Long> {

    public PostService(
            @Qualifier("kafkaPostRepository") PostRepository repository,
            PostRequestDtoConverter postRequestDtoConverter) {
        super(repository, postRequestDtoConverter);
    }

    protected Optional<PostResponseTo> mapResponseTo(Post post) {
        return Optional.ofNullable(PostResponseTo.builder()
                .id(post.getId())
                .newsId(post.getNewsId())
                .content(post.getContent())
                .build());
    }

    @Override
    protected void update(Post one, Post found) {
        one.setNewsId(found.getNewsId());
        one.setContent(found.getContent());
    }

    @Override
    public Optional<PostResponseTo> update(Long aLong, PostRequestTo postRequestTo) {
        PostRepository postRepository = (PostRepository) this.crudRepository;

        postRequestTo.setId(aLong);
        Post updated = postRepository.update(this.dtoConverter.convert(postRequestTo));

        return mapResponseTo(updated);
    }
}
