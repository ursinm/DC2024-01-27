package by.haritonenko.jpa.mapper;

import by.haritonenko.jpa.dto.request.CreateMarkerDto;
import by.haritonenko.jpa.dto.request.UpdateMarkerDto;
import by.haritonenko.jpa.dto.response.MarkerResponseDto;
import by.haritonenko.jpa.model.Marker;
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
