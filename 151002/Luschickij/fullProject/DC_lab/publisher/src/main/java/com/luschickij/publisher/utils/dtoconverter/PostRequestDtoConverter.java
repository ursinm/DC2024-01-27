package com.luschickij.publisher.utils.dtoconverter;

import com.luschickij.publisher.dto.post.PostRequestTo;
import com.luschickij.publisher.model.Post;
import com.luschickij.publisher.repository.NewsRepository;
import com.luschickij.publisher.repository.exception.EntityNotFoundException;
import com.luschickij.publisher.repository.jpa.JpaNewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class PostRequestDtoConverter implements DtoConverter<PostRequestTo, Post> {
    @Override
    public Post convert(PostRequestTo post) {

        return Post.builder()
                .id(post.getId())
                .newsId(post.getNewsId())
                .content(post.getContent())
                .country(post.getCountry())
                .build();
    }
}
