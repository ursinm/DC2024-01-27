package by.bsuir.publisher.service;

import by.bsuir.publisher.dto.request.CreatorRequestDto;
import by.bsuir.publisher.dto.response.CreatorResponseDto;
import by.bsuir.publisher.exception.ResourceNotFoundException;
import by.bsuir.publisher.model.Creator;
import by.bsuir.publisher.repository.CreatorRepository;
import by.bsuir.publisher.service.mapper.CreatorMapper;
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
public class CreatorService {

    private final CreatorRepository creatorRepository;
    private final CreatorMapper creatorMapper;

    @Transactional(readOnly = true)
    public List<CreatorResponseDto> getAll() {
        return creatorMapper.toDto(creatorRepository.findAll());
    }

    @CacheEvict(value = "creator", key = "#id")
    public void deleteById(Long id) {
        Creator entity = creatorRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Creator with id = " + id + " is not found"));
        creatorRepository.delete(entity);
    }

    @CachePut(value = "creator", key = "#result.id()")
    public CreatorResponseDto create(CreatorRequestDto dto) {
        Creator newEntity = creatorMapper.toEntity(dto);
        return creatorMapper.toDto(creatorRepository.save(newEntity));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "creator", key = "#id")
    public CreatorResponseDto getById(Long id) {
        Creator entity = creatorRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Creator with id = " + id + " is not found"));
        return creatorMapper.toDto(entity);
    }

    @CachePut(value = "creator", key = "#result.id()")
    public CreatorResponseDto update(CreatorRequestDto dto) {
        Creator entity = creatorRepository
                .findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Creator with id = " + dto.id() + " is not found"));
        final Creator updated = creatorMapper.partialUpdate(dto, entity);
        return creatorMapper.toDto(creatorRepository.save(updated));
    }
}
