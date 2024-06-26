package by.bsuir.dc.lab5.dto.mappers;

import by.bsuir.dc.lab5.dto.*;
import by.bsuir.dc.lab5.entities.Marker;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MarkerMapper {

    MarkerMapper instance = Mappers.getMapper(MarkerMapper.class);
    Marker convertFromDTO(MarkerRequestTo dto);
    MarkerRequestTo convertRequestToDTO(MarkerRequestTo dto);
    MarkerResponseTo convertToDTO(Marker marker);

}
