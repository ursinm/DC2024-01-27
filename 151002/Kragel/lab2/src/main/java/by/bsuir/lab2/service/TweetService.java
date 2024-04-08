package by.bsuir.lab2.service;

import by.bsuir.lab2.dto.request.TweetRequestDto;
import by.bsuir.lab2.dto.response.TweetResponseDto;
import by.bsuir.lab2.entity.Tweet;
import by.bsuir.lab2.exception.ResourceNotFoundException;
import by.bsuir.lab2.repository.TweetRepository;
import by.bsuir.lab2.service.mapper.TweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TweetService {
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    @Transactional(readOnly = true)
    public List<TweetResponseDto> getAll() {
        return tweetMapper.toDto(tweetRepository.findAll());
    }

    public void deleteById(Long id) {
        Tweet entity = tweetRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet with id = " + id + " is not found"));
        tweetRepository.delete(entity);
    }

    public TweetResponseDto create(TweetRequestDto dto) {
        Tweet newEntity = tweetMapper.toEntity(dto);
        return tweetMapper.toDto(tweetRepository.save(newEntity));
    }

    @Transactional(readOnly = true)
    public TweetResponseDto getById(Long id) {
        Tweet entity = tweetRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet with id = " + id + " is not found"));
        return tweetMapper.toDto(entity);
    }

    public TweetResponseDto update(TweetRequestDto dto) {
        Tweet entity = tweetRepository
                .findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Tweet with id = " + dto.id() + " is not found"));
        final Tweet updated = tweetMapper.partialUpdate(dto, entity);
        return tweetMapper.toDto(tweetRepository.save(updated));
    }
}
