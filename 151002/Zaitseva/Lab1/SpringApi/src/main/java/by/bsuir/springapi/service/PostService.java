package by.bsuir.springapi.service;

import by.bsuir.springapi.dao.impl.PostRepository;
import by.bsuir.springapi.model.request.PostRequestTo;
import by.bsuir.springapi.model.response.PostResponseTo;
import by.bsuir.springapi.service.exceptions.ResourceNotFoundException;
import by.bsuir.springapi.service.exceptions.ResourceStateException;
import by.bsuir.springapi.service.mapper.PostMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class PostService implements RestService<PostRequestTo, PostResponseTo> {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public List<PostResponseTo> findAll() {
        return postMapper.getListResponseTo(postRepository.getAll());
    }

    @Override
    public PostResponseTo findById(Long id) {
        return postMapper.getResponseTo(postRepository
                .getBy(id)
                .orElseThrow(() -> commentNotFoundException(id)));
    }

    @Override
    public PostResponseTo create(PostRequestTo commentTo) {
        return postRepository
                .save(postMapper.getComment(commentTo))
                .map(postMapper::getResponseTo)
                .orElseThrow(PostService::newsStateException);
    }

    @Override
    public PostResponseTo update(PostRequestTo commentTo) {
        postRepository
                .getBy(postMapper.getComment(commentTo).getId())
                .orElseThrow(() -> commentNotFoundException(postMapper.getComment(commentTo).getId()));
        return postRepository
                .update(postMapper.getComment(commentTo))
                .map(postMapper::getResponseTo)
                .orElseThrow(PostService::newsStateException);
    }

    @Override
    public boolean removeById(Long id) {
        if (!postRepository.removeById(id)) {
            throw commentNotFoundException(id);
        }
        return true;
    }

    private static ResourceNotFoundException commentNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find comment with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 83);
    }

    private static ResourceStateException newsStateException() {
        return new ResourceStateException("Failed to create/update comment with specified credentials", HttpStatus.CONFLICT.value() * 100 + 84);
    }
}
