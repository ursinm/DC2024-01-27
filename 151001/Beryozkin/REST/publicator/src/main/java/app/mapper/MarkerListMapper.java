package app.mapper;

import app.dto.MarkerRequestTo;
import app.dto.MarkerResponseTo;
import app.entities.Marker;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = MarkerMapper.class)
public interface MarkerListMapper {
    List<Marker> toMarkerList(List<MarkerRequestTo> markers);
    List<MarkerResponseTo> toMarkerResponseList(List<Marker> markers);
}
