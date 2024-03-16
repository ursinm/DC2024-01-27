package by.bsuir.lab2.service;

import by.bsuir.lab2.dto.request.StickerRequestDto;
import by.bsuir.lab2.dto.response.StickerResponseDto;
import by.bsuir.lab2.entity.Sticker;
import by.bsuir.lab2.exception.ResourceNotFoundException;
import by.bsuir.lab2.repository.StickerRepository;
import by.bsuir.lab2.service.mapper.StickerMapper;
import lombok.RequiredArgsConstructor;
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

    public void deleteById(Long id) {
        Sticker entity = stickerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sticker with id = " + id + " is not found"));
        stickerRepository.delete(entity);
    }

    public StickerResponseDto create(StickerRequestDto dto) {
        Sticker newEntity = stickerMapper.toEntity(dto);
        return stickerMapper.toDto(stickerRepository.save(newEntity));
    }

    @Transactional(readOnly = true)
    public StickerResponseDto getById(Long id) {
        Sticker entity = stickerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sticker with id = " + id + " is not found"));
        return stickerMapper.toDto(entity);
    }

    public StickerResponseDto update(StickerRequestDto dto) {
        Sticker entity = stickerRepository
                .findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Sticker with id = " + dto.id() + " is not found"));
        final Sticker updated = stickerMapper.partialUpdate(dto, entity);
        return stickerMapper.toDto(stickerRepository.save(updated));
    }
}
