package by.bsuir.dc.features.marker;

import by.bsuir.dc.exceptions.BusinessRuleException;
import by.bsuir.dc.exceptions.EntityNotFoundException;
import by.bsuir.dc.exceptions.ErrorMessages;
import by.bsuir.dc.features.marker.dto.MarkerRequestDto;
import by.bsuir.dc.features.marker.dto.MarkerResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class MarkerService {
    private final MarkerRepository markerRepository;

    private final MarkerMapper markerMapper;

    public MarkerResponseDto addMarker(@Valid MarkerRequestDto markerRequestDto) {
        var doesExist = markerRepository.existsByName(markerRequestDto.name());
        if (doesExist) {
            throw new BusinessRuleException(ErrorMessages.markerAlreadyExists);
        }

        var marker = markerMapper.toEntity(markerRequestDto);
        marker = markerRepository.save(marker);
        return markerMapper.toDto(marker);
    }

    public List<MarkerResponseDto> getAllMarkers() {
        var markers = markerRepository.findAll();
        return markerMapper.toDtoList(markers);
    }

    public MarkerResponseDto getById(@Min(1) @Max(Long.MAX_VALUE) Long markerId) {
        var marker = markerRepository.findById(markerId).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessages.markerNotFound));
        return markerMapper.toDto(marker);
    }

    public MarkerResponseDto updateById(
            @Min(1) @Max(Long.MAX_VALUE) Long markerId,
            @Valid MarkerRequestDto markerRequestDto
    ) {
        var doesExist = markerRepository.existsById(markerId);
        if (doesExist) {
            throw new EntityNotFoundException("");
        }

        var marker = markerMapper.toEntity(markerRequestDto);
        marker.setId(markerId);
        markerRepository.save(marker);

        return markerMapper.toDto(marker);
    }

    public void deleteById(@Min(1) @Max(Long.MAX_VALUE) Long markerId) {
        var doesExist = markerRepository.existsById(markerId);
        if (!doesExist) {
            throw new EntityNotFoundException(ErrorMessages.markerNotFound);
        }

        markerRepository.deleteById(markerId);
    }
}
