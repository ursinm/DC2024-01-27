package by.bsuir.mapper;

import by.bsuir.dto.CreatorRequestTo;
import by.bsuir.dto.CreatorResponseTo;
import by.bsuir.entities.Creator;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper(componentModel = "spring", uses = CreatorMapper.class)
public interface CreatorListMapper {
    List<Creator> toCreatorList(List<CreatorRequestTo> creators);
    List<CreatorResponseTo> toCreatorResponseList(List<Creator> creators);
}
