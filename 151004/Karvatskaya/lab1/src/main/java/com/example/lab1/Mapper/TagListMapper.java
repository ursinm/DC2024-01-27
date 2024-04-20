package com.example.lab1.Mapper;

import com.example.lab1.DTO.TagRequestTo;
import com.example.lab1.DTO.TagResponseTo;
import com.example.lab1.Model.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface TagListMapper {
    List<Tag> toTagList(List<TagRequestTo> list);

    List<TagResponseTo> toTagResponseList(List<Tag> list);
}
