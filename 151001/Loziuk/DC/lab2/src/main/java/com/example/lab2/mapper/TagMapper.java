package com.example.lab2.mapper;

import com.example.lab2.dto.TagRequestTo;
import com.example.lab2.dto.TagResponseTo;
import com.example.lab2.model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag tagRequestToTag(TagRequestTo tagRequestTo);

    TagResponseTo tagToTagResponse(Tag tag);
}
