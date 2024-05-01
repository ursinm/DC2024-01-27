package by.bsuir.vladislavmatsiushenko.mapper;

import by.bsuir.vladislavmatsiushenko.dto.TagRequestTo;
import by.bsuir.vladislavmatsiushenko.dto.TagResponseTo;
import by.bsuir.vladislavmatsiushenko.model.Tag;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface TagListMapper {
    List<Tag> toTagList(List<TagRequestTo> list);

    List<TagResponseTo> toTagResponseList(List<Tag> list);
}
