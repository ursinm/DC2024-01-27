package com.example.lab1.mapper;

import com.example.lab1.dto.TagRequestTo;
import com.example.lab1.dto.TagResponseTo;
import com.example.lab1.model.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface TagListMapper {
    List<Tag> toTagList(List<TagRequestTo> list);

    List<TagResponseTo> toTagResponseList(List<Tag> list);
}
