package com.luschickij.discussion.service;

import com.luschickij.discussion.dto.post.PostRequestTo;
import com.luschickij.discussion.dto.post.PostResponseTo;
import com.luschickij.discussion.model.Post;
import com.luschickij.discussion.repository.PostRepository;
import com.luschickij.discussion.utils.dtoconverter.modelassembler.PostRequestDtoConverter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService extends CommonRestService {

    @Autowired
    public PostService(PostRepository repository,
                          PostRequestDtoConverter dtoConverter) {
        super(repository, dtoConverter);
    }

    @Override
    public Optional<PostResponseTo> create(PostRequestTo postRequestTo) {
        return super.create(postRequestTo);
    }

    Optional<PostResponseTo> mapResponseTo(Post post) {
        return Optional.ofNullable(PostResponseTo.builder()
                .id(post.getId())
                .newsId(post.getNewsId())
                .content(post.getContent())
                .country(post.getCountry())
                .build());
    }

    @Override
    void update(Post one, Post found) {
        one.setNewsId(found.getNewsId());
        one.setContent(found.getContent());
    }
}
