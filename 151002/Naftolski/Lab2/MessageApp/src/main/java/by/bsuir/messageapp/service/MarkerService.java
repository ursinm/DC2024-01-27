package by.bsuir.messageapp.service;

import by.bsuir.messageapp.dao.repository.MarkerRepository;
import by.bsuir.messageapp.model.entity.Marker;
import by.bsuir.messageapp.model.request.MarkerRequestTo;
import by.bsuir.messageapp.model.response.MarkerResponseTo;
import by.bsuir.messageapp.service.exceptions.ResourceNotFoundException;
import by.bsuir.messageapp.service.exceptions.ResourceStateException;
import by.bsuir.messageapp.service.mapper.MarkerMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class MarkerService implements IService<MarkerRequestTo, MarkerResponseTo> {
    private final MarkerRepository markerRepository;
    private final MarkerMapper markerMapper;

    @Override
    public MarkerResponseTo findById(Long id) {
        return markerRepository.findById(id).map(markerMapper::getResponse).orElseThrow(() -> findByIdException(id));
    }

    @Override
    public List<MarkerResponseTo> findAll() {
        return markerMapper.getListResponse(markerRepository.findAll());
    }

    @Override
    public MarkerResponseTo create(MarkerRequestTo request) {
        MarkerResponseTo response = markerMapper.getResponse(markerRepository.save(markerMapper.getMarker(request)));

        if (response == null) {
            throw createException();
        }

        return response;
    }

    @Override
    public MarkerResponseTo update(MarkerRequestTo request) {
        MarkerResponseTo response = markerMapper.getResponse(markerRepository.save(markerMapper.getMarker(request)));
        if (response == null) {
            throw updateException();
        }

        return response;
    }

    @Override
    public void removeById(Long id) {
        Marker marker = markerRepository.findById(id).orElseThrow(MarkerService::removeException);

        markerRepository.delete(marker);
    }

    private static ResourceNotFoundException findByIdException(Long id) {
        return new ResourceNotFoundException(HttpStatus.NOT_FOUND.value() * 100 + 21, "Can't find marker by id = " + id);
    }

    private static ResourceStateException createException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 22, "Can't create marker");
    }

    private static ResourceStateException updateException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 23, "Can't update marker");
    }

    private static ResourceStateException removeException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 24, "Can't remove marker");
    }
}
