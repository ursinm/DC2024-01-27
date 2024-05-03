package by.bsuir.publisher.service;

import by.bsuir.publisher.event.InTopicMessage;
import by.bsuir.publisher.event.OutTopicMessage;
import by.bsuir.publisher.event.PostInTopicDto;
import by.bsuir.publisher.event.PostOutTopicDto;
import by.bsuir.publisher.exception.PostExchangeFailedException;
import by.bsuir.publisher.exception.ResourceNotFoundException;
import by.bsuir.publisher.listener.KafkaPostListener;
import by.bsuir.publisher.model.dto.request.PostRequestDto;
import by.bsuir.publisher.model.dto.response.PostResponseDto;
import by.bsuir.publisher.model.entity.PostState;
import by.bsuir.publisher.model.mapper.PostMapper;
import by.bsuir.publisher.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final KafkaPostListener kafkaPostListener;
    private static final SecureRandom random;
    private final IssueRepository issueRepository;

    static {
        SecureRandom randomInstance;
        try {
            randomInstance = SecureRandom.getInstance("NativePRNG");
        } catch (NoSuchAlgorithmException ex) {
            randomInstance = new SecureRandom();
        }
        random = randomInstance;
    }

    private static Long getTimeBasedId(){
        return (((System.currentTimeMillis() << 16) | (random.nextLong() & 0xFFFF)));
    }

    public List<PostResponseDto> getAll() {
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.GET_ALL);
        OutTopicMessage outTopicMsg = kafkaPostListener.sync(inTopicMsg);
        return postMapper.toDto(outTopicMsg.resultList());
    }

    @CacheEvict(value = "post", key = "#id")
    public void deleteById(Long id) {
        PostInTopicDto inTopicDto = new PostInTopicDto();
        inTopicDto.setId(id);
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.DELETE_BY_ID, inTopicDto);
        OutTopicMessage outTopicMsg = kafkaPostListener.sync(inTopicMsg);
        PostOutTopicDto deleted = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Post with id = " + id + " is not found"));
        if (!Objects.equals(id, deleted.id())) {
            throw new PostExchangeFailedException("Post request return invalid id: expected = " + id + ", returned = " + deleted.id());
        }
    }

    @CachePut(value = "post", key = "#result.id()")
    public PostResponseDto create(PostRequestDto dto, Locale locale) {
        Long tweetId = dto.issueId();
        if (!issueRepository.existsById(tweetId)) {
            throw new ResourceNotFoundException("Issue with id = " + tweetId + " doesn't exist");
        }
        PostInTopicDto inTopicDto = postMapper.toInTopicDto(dto);
        inTopicDto.setId(getTimeBasedId());
        inTopicDto.setCountry(locale);
        inTopicDto.setState(PostState.PENDING);
        InTopicMessage inTopicMsg = new InTopicMessage(
                InTopicMessage.Operation.CREATE, inTopicDto);
        OutTopicMessage outTopicMsg = kafkaPostListener.sync(inTopicMsg);
        PostOutTopicDto created = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new PostExchangeFailedException("Getting post with id = "
                        + inTopicDto.getId() + " failed"));
        return postMapper.toDto(created);
    }

    @Cacheable(value = "post", key = "#id")
    public PostResponseDto getById(Long id) {
        PostInTopicDto inTopicDto = new PostInTopicDto();
        inTopicDto.setId(id);
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.GET_BY_ID, inTopicDto);
        OutTopicMessage outTopicMsg = kafkaPostListener.sync(inTopicMsg);
        PostOutTopicDto postOut = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Post with id = " + id + " is not found"));
        return postMapper.toDto(postOut);
    }

    @CachePut(value = "post", key = "#result.id()")
    public PostResponseDto update(PostRequestDto dto) {
        Long tweetId = dto.issueId();
        if (!issueRepository.existsById(tweetId)) {
            throw new ResourceNotFoundException("Issue with id = " + tweetId + " doesn't exist");
        }
        PostInTopicDto inTopicDto = postMapper.toInTopicDto(dto);
        inTopicDto.setState(PostState.PENDING);
        InTopicMessage inTopicMsg = new InTopicMessage(
                InTopicMessage.Operation.UPDATE, inTopicDto);
        OutTopicMessage outTopicMsg = kafkaPostListener.sync(inTopicMsg);
        PostOutTopicDto updated = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new PostExchangeFailedException("Getting post with id = "
                        + inTopicDto.getId() + " failed"));
        return postMapper.toDto(updated);
    }
}
