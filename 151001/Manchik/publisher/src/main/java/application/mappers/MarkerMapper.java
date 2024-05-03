package application.mappers;

import application.dto.MarkerRequestTo;
import application.dto.MarkerResponseTo;
import application.entites.Marker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarkerMapper {
    Marker toMarker(MarkerRequestTo marker);
    MarkerResponseTo toMarkerResponse(Marker marker);
}
