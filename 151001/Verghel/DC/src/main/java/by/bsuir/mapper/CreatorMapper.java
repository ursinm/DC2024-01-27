package by.bsuir.mapper;

import by.bsuir.dto.CreatorRequestTo;
import by.bsuir.dto.CreatorResponseTo;
import by.bsuir.entities.Creator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreatorMapper {
    Creator CreatorRequestToCreator(CreatorRequestTo CreatorRequestTo);

    CreatorResponseTo CreatorToCreatorResponse(Creator Creator);
}

