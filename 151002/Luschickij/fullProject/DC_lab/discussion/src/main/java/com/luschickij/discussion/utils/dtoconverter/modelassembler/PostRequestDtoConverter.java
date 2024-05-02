package com.luschickij.discussion.utils.dtoconverter.modelassembler;

import com.luschickij.discussion.utils.dtoconverter.DtoConverter;
import com.luschickij.discussion.dto.post.PostRequestTo;
import com.luschickij.discussion.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
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
