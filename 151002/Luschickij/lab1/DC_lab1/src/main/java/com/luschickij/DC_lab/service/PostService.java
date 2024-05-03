package com.luschickij.DC_lab.service;

import com.luschickij.DC_lab.dao.repository.PostRepository;
import com.luschickij.DC_lab.model.entity.Post;
import com.luschickij.DC_lab.model.request.PostRequestTo;
import com.luschickij.DC_lab.model.response.PostResponseTo;
import com.luschickij.DC_lab.service.exceptions.ResourceNotFoundException;
import com.luschickij.DC_lab.service.exceptions.ResourceStateException;
import com.luschickij.DC_lab.service.mapper.PostMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class PostService implements IService<PostRequestTo, PostResponseTo>{
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public PostResponseTo findById(Long id) {
        return postRepository.findById(id).map(postMapper::getResponse).orElseThrow(() -> findByIdException(id));
    }

    @Override
    public List<PostResponseTo> findAll() {
        return postMapper.getListResponse(postRepository.findAll());
    }

    @Override
    public PostResponseTo create(PostRequestTo request) {
        PostResponseTo response = postMapper.getResponse(postRepository.save(postMapper.getPost(request)));

        if (response == null) {
            throw createException();
        }

        return response;
    }

    @Override
    public PostResponseTo update(PostRequestTo request) {
        PostResponseTo response = postMapper.getResponse(postRepository.save(postMapper.getPost(request)));
        if (response == null) {
            throw updateException();
        }

        return response;
    }

    @Override
    public void removeById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostService::removeException);

        postRepository.delete(post);
    }

    private static ResourceNotFoundException findByIdException(Long id) {
        return new ResourceNotFoundException(HttpStatus.NOT_FOUND.value() * 100 + 31, "Can't find post by id = " + id);
    }

    private static ResourceStateException createException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 32, "Can't create post");
    }

    private static ResourceStateException updateException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 33, "Can't update post");
    }

    private static ResourceStateException removeException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 34, "Can't remove post");
    }
}
