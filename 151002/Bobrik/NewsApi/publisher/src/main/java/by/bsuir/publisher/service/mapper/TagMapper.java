package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.model.entity.Tag;
import by.bsuir.publisher.model.request.TagRequestTo;
import by.bsuir.publisher.model.response.TagResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResponseTo getResponseTo(Tag tag);

    List<TagResponseTo> getListResponseTo(Iterable<Tag> tags);

    Tag getTag(TagRequestTo tagRequestTo);
}
