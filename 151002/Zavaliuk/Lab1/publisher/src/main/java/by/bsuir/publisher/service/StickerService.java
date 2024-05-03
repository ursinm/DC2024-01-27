package by.bsuir.publisher.service;

import by.bsuir.publisher.dao.StickerRepository;
import by.bsuir.publisher.model.entity.Sticker;
import by.bsuir.publisher.model.request.StickerRequestTo;
import by.bsuir.publisher.model.response.StickerResponseTo;
import by.bsuir.publisher.service.exceptions.ResourceNotFoundException;
import by.bsuir.publisher.service.exceptions.ResourceStateException;
import by.bsuir.publisher.service.mapper.StickerMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@CacheConfig(cacheNames = "stickerCache")
@RequiredArgsConstructor
public class StickerService implements RestService <StickerRequestTo, StickerResponseTo> {
    private final StickerRepository stickerRepository;

    private final StickerMapper stickerMapper;

    @Cacheable(cacheNames = "stickers")
    @Override
    public List<StickerResponseTo> findAll() {
        return stickerMapper.getListResponseTo(stickerRepository.findAll());
    }

    @Cacheable(cacheNames = "stickers", key = "#id", unless = "#result == null")
    @Override
    public StickerResponseTo findById(Long id) {
        return stickerMapper.getResponseTo(stickerRepository
                .findById(id)
                .orElseThrow(() -> stickerNotFoundException(id)));
    }

    @CacheEvict(cacheNames = "stickers", allEntries = true)
    @Override
    public StickerResponseTo create(StickerRequestTo stickerTo) {
        return stickerMapper.getResponseTo(stickerRepository.save(stickerMapper.getSticker(stickerTo)));
    }

    @CacheEvict(cacheNames = "stickers", allEntries = true)
    @Override
    public StickerResponseTo update(StickerRequestTo stickerTo) {
        stickerRepository
                .findById(stickerMapper.getSticker(stickerTo).getId())
                .orElseThrow(() -> stickerNotFoundException(stickerMapper.getSticker(stickerTo).getId()));
        return stickerMapper.getResponseTo(stickerRepository.save(stickerMapper.getSticker(stickerTo)));
    }

    @Caching(evict = { @CacheEvict(cacheNames = "stickers", key = "#id"),
            @CacheEvict(cacheNames = "stickers", allEntries = true) })    @Override
    public void removeById(Long id) {
        Sticker sticker = stickerRepository
                .findById(id)
                .orElseThrow(() -> stickerNotFoundException(id));
        stickerRepository.delete(sticker);
    }

    private static ResourceNotFoundException stickerNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find sticker with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 33);
    }

    private static ResourceStateException stickerStateException() {
        return new ResourceStateException("Failed to create/update stiicke with specified credentials", HttpStatus.CONFLICT.value() * 100 + 34);
    }
}
