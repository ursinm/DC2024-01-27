package by.bsuir.restapi.service;

import by.bsuir.restapi.exception.ResourceNotFoundException;
import by.bsuir.restapi.model.dto.request.PostRequestDto;
import by.bsuir.restapi.model.dto.response.PostResponseDto;
import by.bsuir.restapi.model.entity.Post;
import by.bsuir.restapi.model.mapper.PostMapper;
import by.bsuir.restapi.repository.IssueRepository;
import by.bsuir.restapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    
    private final PostRepository postRepository;
    private final IssueRepository issueRepository;
    private final PostMapper postMapper;

    public PostResponseDto create(PostRequestDto dto) {
        Post post = postMapper.toEntity(dto);
        return postMapper.toDto(postRepository.save(post));
    }

    public PostResponseDto update(PostRequestDto dto) {
        Post post = postRepository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Post with id %s not found", dto.id())));
        final Post updatedPost = postMapper.partialUpdate(dto, post);
        return postMapper.toDto(postRepository.save(updatedPost));
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Post with id %s not found", id)));
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDto get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Post with id %s not found", id)));
        return postMapper.toDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAll() {
        return postMapper.toDto(postRepository.findAll());
    }
}
