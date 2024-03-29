package by.bsuir.dc.features.post;

import by.bsuir.dc.exceptions.EntityNotFoundException;
import by.bsuir.dc.exceptions.ErrorMessages;
import by.bsuir.dc.features.news.NewsRepository;
import by.bsuir.dc.features.post.dto.PostResponseDto;
import by.bsuir.dc.features.post.dto.PostRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final NewsRepository newsRepository;

    private final PostMapper postMapper;

    public PostResponseDto addPost(@Valid PostRequestDto postRequestDto) {
        boolean doesExist = newsRepository.existsById(postRequestDto.newsId());
        if (doesExist) {
            throw new EntityNotFoundException(ErrorMessages.newsNotFound);
        }

        var post = postMapper.toEntity(postRequestDto);
        post = postRepository.save(post);

        return postMapper.toDto(post);
    }

    public List<PostResponseDto> getAll() {
        var posts = postRepository.findAll();
        return postMapper.toDtoList(posts);
    }

    public PostResponseDto getById(@Min(1) @Max(Long.MAX_VALUE) Long postId) {
        var post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("")
        );
        return postMapper.toDto(post);
    }

    public PostResponseDto update(
            @Min(1) @Max(Long.MAX_VALUE) Long postId,
            @Valid PostRequestDto postRequestDto
    ) {
        var doesExist = postRepository.existsById(postId);
        if (doesExist) {
            throw new EntityNotFoundException("");
        }

        var post = postMapper.toEntity(postRequestDto);
        post.setId(postId);
        postRepository.save(post);

        return postMapper.toDto(post);
    }

    public void deleteById(@Min(1) @Max(Long.MAX_VALUE) Long postId) {
        var doesExists = postRepository.existsById(postId);
        if (doesExists) {
            throw new EntityNotFoundException("");
        }

        postRepository.deleteById(postId);
    }
}
