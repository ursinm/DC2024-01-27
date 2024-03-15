package by.bsuir.dc.service;

import by.bsuir.dc.dto.request.StickerRequestDto;
import by.bsuir.dc.dto.response.StickerResponseDto;
import by.bsuir.dc.entity.Sticker;
import by.bsuir.dc.exception.ResourceNotFoundException;
import by.bsuir.dc.repository.StickerRepository;
import by.bsuir.dc.service.mapper.StickerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StickerService {

    private final StickerRepository stickerRepository;
    private final StickerMapper stickerMapper;

    public List<StickerResponseDto> getAll() {
        return stickerMapper.toDto(stickerRepository.getAll());
    }

    public void deleteById(Long id) {
        Sticker entity = stickerRepository
                .getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sticker with id = " + id + " is not found"));
        stickerRepository.delete(entity);
    }

    public StickerResponseDto create(StickerRequestDto dto) {
        Sticker newEntity = stickerMapper.toEntity(dto);
        return stickerMapper.toDto(stickerRepository.save(newEntity));
    }

    public StickerResponseDto getById(Long id) {
        Sticker entity = stickerRepository
                .getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sticker with id = " + id + " is not found"));
        return stickerMapper.toDto(entity);
    }

    public StickerResponseDto update(StickerRequestDto dto) {
        Sticker entity = stickerRepository
                .getById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Sticker with id = " + dto.id() + " is not found"));
        if (dto.name() != null)
            entity.setName(dto.name());
        return stickerMapper.toDto(stickerRepository.save(entity));
    }
}
