package by.bsuir.vladislavmatsiushenko.api;

import by.bsuir.vladislavmatsiushenko.impl.bean.Tag;
import by.bsuir.vladislavmatsiushenko.impl.dto.TagRequestTo;
import by.bsuir.vladislavmatsiushenko.impl.dto.TagResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);
    TagResponseTo TagToTagResponseTo(Tag tag);
    TagRequestTo TagToTagRequestTo(Tag tag);
    Tag TagResponseToToTag(TagResponseTo responseTo);
    Tag TagRequestToToTag(TagRequestTo requestTo);
}
