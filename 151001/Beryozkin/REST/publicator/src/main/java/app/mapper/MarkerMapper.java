package app.mapper;

import app.entities.Marker;
import app.dto.MarkerRequestTo;
import app.dto.MarkerResponseTo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarkerMapper {
    Marker markerRequestToMarker (MarkerRequestTo markerRequestTo);
    MarkerResponseTo markerToMarkerResponse(Marker marker);
}

