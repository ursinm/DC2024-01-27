package by.bsuir.dc.service;

import by.bsuir.dc.dto.request.CreatorRequestDto;
import by.bsuir.dc.dto.response.CreatorResponseDto;
import by.bsuir.dc.entity.Creator;
import by.bsuir.dc.exception.ResourceNotFoundException;
import by.bsuir.dc.repository.CreatorRepository;
import by.bsuir.dc.service.mapper.CreatorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatorService {

    private final CreatorRepository creatorRepository;
    private final CreatorMapper creatorMapper;

    public List<CreatorResponseDto> getAll() {
        return creatorMapper.toDto(creatorRepository.getAll());
    }

    public void deleteById(Long id) {
        Creator entity = creatorRepository
                .getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Creator with id = " + id + " is not found"));
        creatorRepository.delete(entity);
    }

    public CreatorResponseDto create(CreatorRequestDto dto) {
        Creator newEntity = creatorMapper.toEntity(dto);
        return creatorMapper.toDto(creatorRepository.save(newEntity));
    }

    public CreatorResponseDto getById(Long id) {
        Creator entity = creatorRepository
                .getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Creator with id = " + id + " is not found"));
        return creatorMapper.toDto(entity);
    }

    public CreatorResponseDto update(CreatorRequestDto dto) {
        Creator entity = creatorRepository
                .getById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Creator with id = " + dto.id() + " is not found"));
        if (dto.login() != null)
            entity.setLogin(dto.login());
        if (dto.password() != null)
            entity.setPassword(dto.password());
        if (dto.firstname() != null)
            entity.setFirstName(dto.firstname());
        if (dto.lastname() != null)
            entity.setLastName(dto.lastname());
        return creatorMapper.toDto(creatorRepository.save(entity));
    }
}
