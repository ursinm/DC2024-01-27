package by.bsuir.restapi.model.mapper;

import by.bsuir.restapi.model.dto.request.MarkerRequestTo;
import by.bsuir.restapi.model.dto.response.MarkerResponseTo;
import by.bsuir.restapi.model.entity.Marker;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MarkerMapper {

    MarkerResponseTo toDto(Marker Marker);

    Marker toEntity(MarkerRequestTo MarkerRequestTo);
}
