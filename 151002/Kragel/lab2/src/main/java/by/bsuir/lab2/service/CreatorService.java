package by.bsuir.lab2.service;

import by.bsuir.lab2.dto.request.CreatorRequestDto;
import by.bsuir.lab2.dto.response.CreatorResponseDto;
import by.bsuir.lab2.entity.Creator;
import by.bsuir.lab2.exception.ResourceNotFoundException;
import by.bsuir.lab2.repository.CreatorRepository;
import by.bsuir.lab2.service.mapper.CreatorMapper;
import lombok.RequiredArgsConstructor;
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

    public void deleteById(Long id) {
        Creator entity = creatorRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Creator with id = " + id + " is not found"));
        creatorRepository.delete(entity);
    }

    public CreatorResponseDto create(CreatorRequestDto dto) {
        Creator newEntity = creatorMapper.toEntity(dto);
        return creatorMapper.toDto(creatorRepository.save(newEntity));
    }

    @Transactional(readOnly = true)
    public CreatorResponseDto getById(Long id) {
        Creator entity = creatorRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Creator with id = " + id + " is not found"));
        return creatorMapper.toDto(entity);
    }

    public CreatorResponseDto update(CreatorRequestDto dto) {
        Creator entity = creatorRepository
                .findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Creator with id = " + dto.id() + " is not found"));
        final Creator updated = creatorMapper.partialUpdate(dto, entity);
        return creatorMapper.toDto(creatorRepository.save(updated));
    }
}
