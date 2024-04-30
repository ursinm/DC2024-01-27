package by.bsuir.publisher.service;

import by.bsuir.publisher.dto.request.StickerRequestDto;
import by.bsuir.publisher.dto.response.StickerResponseDto;
import by.bsuir.publisher.exception.ResourceNotFoundException;
import by.bsuir.publisher.model.Sticker;
import by.bsuir.publisher.repository.StickerRepository;
import by.bsuir.publisher.service.mapper.StickerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StickerService {

    private final StickerRepository stickerRepository;
    private final StickerMapper stickerMapper;

    @Transactional(readOnly = true)
    public List<StickerResponseDto> getAll() {
        return stickerMapper.toDto(stickerRepository.findAll());
    }

    @CacheEvict(value = "sticker", key = "#id")
    public void deleteById(Long id) {
        Sticker entity = stickerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sticker with id = " + id + " is not found"));
        stickerRepository.delete(entity);
    }

    @CachePut(value = "sticker", key = "#result.id()")
    public StickerResponseDto create(StickerRequestDto dto) {
        Sticker newEntity = stickerMapper.toEntity(dto);
        return stickerMapper.toDto(stickerRepository.save(newEntity));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "sticker", key = "#id")
    public StickerResponseDto getById(Long id) {
        Sticker entity = stickerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sticker with id = " + id + " is not found"));
        return stickerMapper.toDto(entity);
    }

    @CachePut(value = "sticker", key = "#result.id()")
    public StickerResponseDto update(StickerRequestDto dto) {
        Sticker entity = stickerRepository
                .findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Sticker with id = " + dto.id() + " is not found"));
        final Sticker updated = stickerMapper.partialUpdate(dto, entity);
        return stickerMapper.toDto(stickerRepository.save(updated));
    }
}
