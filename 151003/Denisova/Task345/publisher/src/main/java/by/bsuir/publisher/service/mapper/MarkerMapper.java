package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.model.entity.Marker;
import by.bsuir.publisher.model.request.MarkerRequestTo;
import by.bsuir.publisher.model.response.MarkerResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MarkerMapper {
    MarkerResponseTo getResponseTo(Marker marker);

    List<MarkerResponseTo> getListResponseTo(Iterable<Marker> tags);

    Marker getTag(MarkerRequestTo markerRequestTo);
}
