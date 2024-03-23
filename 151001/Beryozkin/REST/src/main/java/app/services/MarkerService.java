package app.services;

import app.dao.MarkerDao;
import app.dto.MarkerRequestTo;
import app.dto.MarkerResponseTo;
import app.entities.Marker;
import app.exceptions.DeleteException;
import app.exceptions.NotFoundException;
import app.exceptions.UpdateException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import app.mapper.MarkerListMapper;
import app.mapper.MarkerMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    MarkerDao markerDao;
    @Autowired
    MarkerListMapper markerListMapper;

    public MarkerResponseTo getMarkerById(@Min(0) Long id) throws NotFoundException {
        Optional<Marker> marker = markerDao.findById(id);
        return marker.map(value -> markerMapper.markerToMarkerResponse(value)).orElseThrow(() -> new NotFoundException("marker not found!", 40004L));
    }

    public List<MarkerResponseTo> getMarkers() {
        return markerListMapper.toMarkerResponseList(markerDao.findAll());
    }

    public MarkerResponseTo saveMarker(@Valid MarkerRequestTo marker) {
        Marker markerToSave = markerMapper.markerRequestToMarker(marker);
        return markerMapper.markerToMarkerResponse(markerDao.save(markerToSave));
    }

    public void deleteMarker(@Min(0) Long id) throws DeleteException {
        markerDao.delete(id);
    }

    public MarkerResponseTo updateMarker(@Valid MarkerRequestTo marker) throws UpdateException {
        Marker markerToUpdate = markerMapper.markerRequestToMarker(marker);
        return markerMapper.markerToMarkerResponse(markerDao.update(markerToUpdate));
    }

    public MarkerResponseTo getMarkerByTweetId(@Min(0) Long tweetId) throws NotFoundException {
        Optional<Marker> marker = markerDao.getMarkerByTweetId(tweetId);
        return marker.map(value -> markerMapper.markerToMarkerResponse(value)).orElseThrow(() -> new NotFoundException("marker not found!", 40004L));
    }
}
