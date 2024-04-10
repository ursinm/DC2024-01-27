package com.example.lab2.mapper;

import com.example.lab2.dto.TagRequestTo;
import com.example.lab2.dto.TagResponseTo;
import com.example.lab2.model.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface TagListMapper {
    List<Tag> toTagList(List<TagRequestTo> list);

    List<TagResponseTo> toTagResponseList(List<Tag> list);
}
