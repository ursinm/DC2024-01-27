package by.bsuir.newsapi.service.mapper;

import by.bsuir.newsapi.model.entity.Editor;
import by.bsuir.newsapi.model.entity.Tag;
import by.bsuir.newsapi.model.request.EditorRequestTo;
import by.bsuir.newsapi.model.request.TagRequestTo;
import by.bsuir.newsapi.model.response.EditorResponseTo;
import by.bsuir.newsapi.model.response.TagResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResponseTo getResponseTo(Tag tag);

    List<TagResponseTo> getListResponseTo(Iterable<Tag> tags);

    Tag getTag(TagRequestTo tagRequestTo);
}
