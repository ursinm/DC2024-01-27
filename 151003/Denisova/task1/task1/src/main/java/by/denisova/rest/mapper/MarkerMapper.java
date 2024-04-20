package by.denisova.rest.mapper;

import by.denisova.rest.dto.request.CreateMarkerDto;
import by.denisova.rest.dto.request.UpdateMarkerDto;
import by.denisova.rest.dto.response.MarkerResponseDto;
import by.denisova.rest.model.Marker;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface MarkerMapper {
    Marker toMarker(CreateMarkerDto markerRequest);

    MarkerResponseDto toMarkerResponse(Marker marker);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Marker toMarker(UpdateMarkerDto markerRequest, @MappingTarget Marker marker);
}
