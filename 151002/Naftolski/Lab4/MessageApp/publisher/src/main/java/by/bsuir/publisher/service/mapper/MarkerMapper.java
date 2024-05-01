package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.model.entity.Marker;
import by.bsuir.publisher.model.request.MarkerRequestTo;
import by.bsuir.publisher.model.response.MarkerResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MarkerMapper {
    MarkerResponseTo getResponse(Marker marker);
    List<MarkerResponseTo> getListResponse(Iterable<Marker> markers);
    @Mapping(target = "stories", ignore = true)
    Marker getMarker(MarkerRequestTo markerRequestTo);
}
