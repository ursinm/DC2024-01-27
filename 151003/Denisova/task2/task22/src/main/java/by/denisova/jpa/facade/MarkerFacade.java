package by.denisova.jpa.facade;

import by.denisova.jpa.dto.request.CreateMarkerDto;
import by.denisova.jpa.dto.request.UpdateMarkerDto;
import by.denisova.jpa.dto.response.MarkerResponseDto;
import by.denisova.jpa.mapper.MarkerMapper;
import by.denisova.jpa.model.Marker;
import by.denisova.jpa.service.MarkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MarkerFacade {

    private final MarkerService markerService;
    private final MarkerMapper markerMapper;

    @Transactional(readOnly = true)
    public MarkerResponseDto findById(Long id) {
        Marker marker = markerService.findById(id);
        return markerMapper.toMarkerResponse(marker);
    }

    @Transactional(readOnly = true)
    public List<MarkerResponseDto> findAll() {
        List<Marker> markers = markerService.findAll();

        return markers.stream().map(markerMapper::toMarkerResponse).toList();
    }

    @Transactional
    public MarkerResponseDto save(CreateMarkerDto markerRequest) {
        Marker marker = markerMapper.toMarker(markerRequest);

        Marker savedMarker = markerService.save(marker);

        return markerMapper.toMarkerResponse(savedMarker);
    }

    @Transactional
    public MarkerResponseDto update(UpdateMarkerDto markerRequest) {
        Marker marker = markerService.findById(markerRequest.getId());

        Marker updatedMarker = markerMapper.toMarker(markerRequest, marker);

        return markerMapper.toMarkerResponse(markerService.update(updatedMarker));
    }

    @Transactional
    public void delete(Long id) {
        markerService.deleteById(id);
    }
}
