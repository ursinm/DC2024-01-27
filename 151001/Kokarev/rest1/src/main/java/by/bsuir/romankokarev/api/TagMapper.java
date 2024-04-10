package by.bsuir.romankokarev.api;

import by.bsuir.romankokarev.impl.bean.Tag;
import by.bsuir.romankokarev.impl.dto.TagRequestTo;
import by.bsuir.romankokarev.impl.dto.TagResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);
    Tag TagResponseToToTag(TagResponseTo responseTo);
    Tag TagRequestToToTag(TagRequestTo requestTo);
    TagRequestTo TagToTagRequestTo(Tag tag);
    TagResponseTo TagToTagResponseTo(Tag tag);
}
