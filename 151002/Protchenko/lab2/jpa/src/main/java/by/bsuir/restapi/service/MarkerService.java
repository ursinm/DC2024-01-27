package by.bsuir.restapi.service;

import by.bsuir.restapi.exception.ResourceNotFoundException;
import by.bsuir.restapi.model.dto.request.MarkerRequestDto;
import by.bsuir.restapi.model.dto.response.MarkerResponseDto;
import by.bsuir.restapi.model.entity.Marker;
import by.bsuir.restapi.model.mapper.MarkerMapper;
import by.bsuir.restapi.repository.MarkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MarkerService {

    private final MarkerRepository markerRepository;
    private final MarkerMapper markerMapper;

    public MarkerResponseDto create(MarkerRequestDto dto) {
        Marker marker = markerMapper.toEntity(dto);
        return markerMapper.toDto(markerRepository.save(marker));
    }

    public MarkerResponseDto update(MarkerRequestDto dto) {
        Marker marker = markerRepository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Marker with id %s not found", dto.id())));
        final Marker updatedMarker = markerMapper.partialUpdate(dto, marker);
        return markerMapper.toDto(markerRepository.save(updatedMarker));
    }

    public void delete(Long id) {
        Marker marker = markerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Marker with id %s not found", id)));
        markerRepository.delete(marker);
    }

    @Transactional(readOnly = true)
    public MarkerResponseDto get(Long id) {
        Marker marker = markerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Marker with id %s not found", id)));
        return markerMapper.toDto(marker);
    }

    @Transactional(readOnly = true)
    public List<MarkerResponseDto> getAll() {
        return markerMapper.toDto(markerRepository.findAll());
    }
}
