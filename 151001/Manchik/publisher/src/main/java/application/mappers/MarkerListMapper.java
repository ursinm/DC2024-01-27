package application.mappers;

import application.dto.MarkerRequestTo;
import application.dto.MarkerResponseTo;
import application.entites.Marker;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = MarkerMapper.class)
public interface MarkerListMapper {
    List<Marker> toMarkerList(List<MarkerRequestTo> marker);
    List<MarkerResponseTo> toMarkerResponseList(List<Marker> marker);
}
