package com.example.lab1.mapper;

import com.example.lab1.dto.TagRequestTo;
import com.example.lab1.dto.TagResponseTo;
import com.example.lab1.model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag tagRequestToTag(TagRequestTo tagRequestTo);

    TagResponseTo tagToTagResponse(Tag tag);
}
