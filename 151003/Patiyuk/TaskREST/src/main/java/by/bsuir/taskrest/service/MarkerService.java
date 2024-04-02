package by.bsuir.taskrest.service;

import by.bsuir.taskrest.dto.request.MarkerRequestTo;
import by.bsuir.taskrest.dto.response.MarkerResponseTo;

import java.util.List;

public interface MarkerService {
    List<MarkerResponseTo> getAllMarkers();
    MarkerResponseTo getMarkerById(Long id);
    MarkerResponseTo createMarker(MarkerRequestTo marker);
    MarkerResponseTo updateMarker(MarkerRequestTo marker);
    MarkerResponseTo updateMarker(Long id, MarkerRequestTo marker);
    void deleteMarker(Long id);
}
