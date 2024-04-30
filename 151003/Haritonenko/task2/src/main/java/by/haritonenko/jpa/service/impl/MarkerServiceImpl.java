package by.haritonenko.jpa.service.impl;

import by.haritonenko.jpa.exception.EntityNotFoundException;
import by.haritonenko.jpa.model.Marker;
import by.haritonenko.jpa.repository.impl.MarkerRepository;
import by.haritonenko.jpa.service.MarkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkerServiceImpl implements MarkerService {

    public static final String MARKER_NOT_FOUND_MESSAGE = "Marker with id '%d' doesn't exist";
    private final MarkerRepository markerRepository;

    @Override
    public Marker findById(Long id) {
        return markerRepository.findById(id).orElseThrow(() -> {
            final String message = MARKER_NOT_FOUND_MESSAGE.formatted(id);
            return new EntityNotFoundException(message);
        });
    }

    @Override
    public void deleteById(Long id) {
        markerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(MARKER_NOT_FOUND_MESSAGE));

        markerRepository.deleteById(id);
    }

    @Override
    public Marker save(Marker marker) {
        return markerRepository.save(marker);
    }

    @Override
    public Marker update(Marker marker) {
        markerRepository.findById(marker.getId()).orElseThrow(()-> new EntityNotFoundException(MARKER_NOT_FOUND_MESSAGE));

        return markerRepository.save(marker);
    }

    @Override
    public List<Marker> findAll() {
        return markerRepository.findAll();
    }
}
