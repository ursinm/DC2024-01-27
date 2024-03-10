package by.bsuir.dc.service;

import by.bsuir.dc.dto.request.TweetRequestDto;
import by.bsuir.dc.dto.response.TweetResponseDto;
import by.bsuir.dc.entity.Tweet;
import by.bsuir.dc.exception.ResourceNotFoundException;
import by.bsuir.dc.repository.TweetRepository;
import by.bsuir.dc.service.mapper.TweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    public List<TweetResponseDto> getAll() {
        return tweetMapper.toDto(tweetRepository.getAll());
    }

    public void deleteById(Long id) {
        Tweet entity = tweetRepository
                .getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet with id = " + id + " is not found"));
        tweetRepository.delete(entity);
    }

    public TweetResponseDto create(TweetRequestDto dto) {
        Tweet newEntity = tweetMapper.toEntity(dto);
        return tweetMapper.toDto(tweetRepository.save(newEntity));
    }

    public TweetResponseDto getById(Long id) {
        Tweet entity = tweetRepository
                .getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet with id = " + id + " is not found"));
        return tweetMapper.toDto(entity);
    }

    public TweetResponseDto update(TweetRequestDto dto) {
        Tweet entity = tweetRepository
                .getById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Tweet with id = " + dto.id() + " is not found"));
        if (dto.creatorId() != null)
            entity.setCreatorId(dto.creatorId());
        if (dto.title() != null)
            entity.setTitle(dto.title());
        if (dto.content() != null)
            entity.setContent(dto.content());
        return tweetMapper.toDto(tweetRepository.save(entity));
    }
}
