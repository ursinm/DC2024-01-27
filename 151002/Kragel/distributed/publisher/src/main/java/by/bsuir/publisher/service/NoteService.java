package by.bsuir.publisher.service;

import by.bsuir.publisher.dto.request.NoteRequestDto;
import by.bsuir.publisher.dto.response.NoteResponseDto;
import by.bsuir.publisher.event.*;
import by.bsuir.publisher.exception.NoteExchangeFailedException;
import by.bsuir.publisher.exception.ResourceNotFoundException;
import by.bsuir.publisher.model.NoteState;
import by.bsuir.publisher.repository.TweetRepository;
import by.bsuir.publisher.service.mapper.NoteMapper;
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
public class NoteService {
    private final NoteMapper noteMapper;
    private final KafkaNoteClient kafkaNoteClient;
    private static final SecureRandom random;
    private final TweetRepository tweetRepository;

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

    public List<NoteResponseDto> getAll() {
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.GET_ALL);
        OutTopicMessage outTopicMsg = kafkaNoteClient.sync(inTopicMsg);
        return noteMapper.toDto(outTopicMsg.resultList());
    }

    @CacheEvict(value = "note", key = "#id")
    public void deleteById(Long id) {
        NoteInTopicDto inTopicDto = new NoteInTopicDto();
        inTopicDto.setId(id);
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.DELETE_BY_ID, inTopicDto);
        OutTopicMessage outTopicMsg = kafkaNoteClient.sync(inTopicMsg);
        NoteOutTopicDto deleted = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + id + " is not found"));
        if (!Objects.equals(id, deleted.id())) {
            throw new NoteExchangeFailedException("Note request return invalid id: expected = " + id + ", returned = " + deleted.id());
        }
    }

    @CachePut(value = "note", key = "#result.id()")
    public NoteResponseDto create(NoteRequestDto dto, Locale locale) {
        Long tweetId = dto.tweetId();
        if (!tweetRepository.existsById(tweetId)) {
            throw new ResourceNotFoundException("Tweet with id = " + tweetId + " doesn't exist");
        }
        NoteInTopicDto inTopicDto = noteMapper.toInTopicDto(dto);
        inTopicDto.setId(getTimeBasedId());
        inTopicDto.setCountry(locale);
        inTopicDto.setState(NoteState.PENDING);
        InTopicMessage inTopicMsg = new InTopicMessage(
                InTopicMessage.Operation.CREATE, inTopicDto);
        OutTopicMessage outTopicMsg = kafkaNoteClient.sync(inTopicMsg);
        NoteOutTopicDto created = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoteExchangeFailedException("Getting note with id = "
                        + inTopicDto.getId() + " failed"));
        return noteMapper.toDto(created);
    }

    @Cacheable(value = "note", key = "#id")
    public NoteResponseDto getById(Long id) {
        NoteInTopicDto inTopicDto = new NoteInTopicDto();
        inTopicDto.setId(id);
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.GET_BY_ID, inTopicDto);
        OutTopicMessage outTopicMsg = kafkaNoteClient.sync(inTopicMsg);
        NoteOutTopicDto noteOut = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + id + " is not found"));
        return noteMapper.toDto(noteOut);
    }

    @CachePut(value = "note", key = "#result.id()")
    public NoteResponseDto update(NoteRequestDto dto) {
        Long tweetId = dto.tweetId();
        if (!tweetRepository.existsById(tweetId)) {
            throw new ResourceNotFoundException("Tweet with id = " + tweetId + " doesn't exist");
        }
        NoteInTopicDto inTopicDto = noteMapper.toInTopicDto(dto);
        inTopicDto.setState(NoteState.PENDING);
        InTopicMessage inTopicMsg = new InTopicMessage(
                InTopicMessage.Operation.UPDATE, inTopicDto);
        OutTopicMessage outTopicMsg = kafkaNoteClient.sync(inTopicMsg);
        NoteOutTopicDto updated = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoteExchangeFailedException("Getting note with id = "
                        + inTopicDto.getId() + " failed"));
        return noteMapper.toDto(updated);
    }
}