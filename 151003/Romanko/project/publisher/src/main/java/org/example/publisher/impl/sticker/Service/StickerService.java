package org.example.publisher.impl.sticker.Service;

import org.example.publisher.api.exception.DuplicateEntityException;
import org.example.publisher.api.exception.EntityNotFoundException;
import org.example.publisher.impl.sticker.Sticker;
import org.example.publisher.impl.sticker.StickerRepository;
import org.example.publisher.impl.sticker.dto.StickerRequestTo;
import org.example.publisher.impl.sticker.dto.StickerResponseTo;
import org.example.publisher.impl.sticker.mapper.Impl.StickerMapperImpl;
import org.example.publisher.impl.story.Story;
import org.example.publisher.impl.story.StoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "stickerCache")
public class StickerService {

    private final StickerRepository stickerRepository;
    private final StoryRepository storyRepository;

    private final StickerMapperImpl stickerMapper;
    private final String ENTITY_NAME = "sticker";


    @Cacheable(cacheNames = "stickers")
    public List<StickerResponseTo> getStickers() {
        List<Sticker> stickers = stickerRepository.findAll();
        List<StickerResponseTo> stickersTos = new ArrayList<>();
        for (var sticker: stickers) {
            stickersTos.add(stickerMapper.stickerToResponseTo(sticker));
        }
        return stickersTos;
    }

    @Cacheable(cacheNames = "stickers", key = "#id", unless = "#result == null")
    public StickerResponseTo getStickerById(BigInteger id) throws EntityNotFoundException {
        Optional<Sticker> sticker = stickerRepository.findById(id);
        if (sticker.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return stickerMapper.stickerToResponseTo(sticker.get());
    }

    @CacheEvict(cacheNames = "stickers", allEntries = true)
    public StickerResponseTo saveSticker(StickerRequestTo sticker) throws DuplicateEntityException {
        List<Story> storys = new ArrayList<>();
        if (sticker.getStoryIds() != null) {
            storys = storyRepository.findAllById(sticker.getStoryIds());
        }
        Sticker entity = stickerMapper.dtoToEntity(sticker, storys);
        try {
            Sticker savedSticker = stickerRepository.save(entity);
            return stickerMapper.stickerToResponseTo(savedSticker);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateEntityException(ENTITY_NAME, "");
        }

    }

    @CacheEvict(cacheNames = "stickers", allEntries = true)
    public StickerResponseTo updateSticker(StickerRequestTo stickerRequestTo) throws EntityNotFoundException, DuplicateEntityException {
        Optional<Sticker> stickerEntity = stickerRepository.findById(stickerRequestTo.getId());
        if (stickerEntity.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, stickerRequestTo.getId());
        }
        List<Story> storys = new ArrayList<>();
        if (stickerRequestTo.getStoryIds() != null) {
            storys = storyRepository.findAllById(stickerRequestTo.getStoryIds());
        }
        try {
            Sticker sticker = stickerRepository.save(stickerMapper.dtoToEntity(stickerRequestTo, storys));
            return stickerMapper.stickerToResponseTo(sticker);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateEntityException(ENTITY_NAME, "");
        }
    }

    @Caching(evict = { @CacheEvict(cacheNames = "stickers", key = "#id"),
            @CacheEvict(cacheNames = "stickers", allEntries = true) })
    public void deleteStickerById(BigInteger id) throws EntityNotFoundException {
        if (stickerRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        stickerRepository.deleteById(id);
    }

}
