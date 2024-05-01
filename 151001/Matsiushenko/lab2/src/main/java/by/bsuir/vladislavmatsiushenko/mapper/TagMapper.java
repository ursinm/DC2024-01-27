package by.bsuir.vladislavmatsiushenko.mapper;

import by.bsuir.vladislavmatsiushenko.dto.TagRequestTo;
import by.bsuir.vladislavmatsiushenko.dto.TagResponseTo;
import by.bsuir.vladislavmatsiushenko.model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag tagRequestToTag(TagRequestTo tagRequestTo);

    TagResponseTo tagToTagResponse(Tag tag);
}
