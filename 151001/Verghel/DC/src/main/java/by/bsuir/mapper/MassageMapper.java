package by.bsuir.mapper;

import by.bsuir.dto.MassageRequestTo;
import by.bsuir.dto.MassageResponseTo;
import by.bsuir.entities.Massage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MassageMapper {
    Massage MassageRequestToMassage(MassageRequestTo MassageRequestTo);

    MassageResponseTo MassageToMassageResponse(Massage Massage);
}
