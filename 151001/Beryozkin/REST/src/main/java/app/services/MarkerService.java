package app.services;

import app.dto.MarkerRequestTo;
import app.dto.MarkerResponseTo;
import app.entities.Marker;
import app.exceptions.DeleteException;
import app.exceptions.NotFoundException;
import app.exceptions.UpdateException;
import app.mapper.MarkerListMapper;
import app.mapper.MarkerMapper;
import app.repository.MarkerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class MarkerService {
    @Autowired
    MarkerMapper markerMapper;
    @Autowired
    MarkerRepository markerDao;
    @Autowired
    MarkerListMapper markerListMapper;

    public MarkerResponseTo getMarkerById(@Min(0) Long id) throws NotFoundException {
        Optional<Marker> marker = markerDao.findById(id);
        return marker.map(value -> markerMapper.markerToMarkerResponse(value)).orElseThrow(() -> new NotFoundException("marker not found!", 40004L));
    }

    public List<MarkerResponseTo> getMarkers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder != null && sortOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Marker> markers = markerDao.findAll(pageable);
        return markerListMapper.toMarkerResponseList(markers.toList());
    }

    public MarkerResponseTo saveMarker(@Valid MarkerRequestTo marker) {
        Marker markerToSave = markerMapper.markerRequestToMarker(marker);
        return markerMapper.markerToMarkerResponse(markerDao.save(markerToSave));
    }

    public void deleteMarker(@Min(0) Long id) throws DeleteException {
        if (!markerDao.existsById(id)) {
            throw new DeleteException("Marker not found!", 40004L);
        } else {
            markerDao.deleteById(id);
        }
    }

    public MarkerResponseTo updateMarker(@Valid MarkerRequestTo marker) throws UpdateException {
        Marker markerToUpdate = markerMapper.markerRequestToMarker(marker);
        if (!markerDao.existsById(markerToUpdate.getId())) {
            throw new UpdateException("Marker not found!", 40004L);
        } else {
            return markerMapper.markerToMarkerResponse(markerDao.save(markerToUpdate));
        }
    }
}
