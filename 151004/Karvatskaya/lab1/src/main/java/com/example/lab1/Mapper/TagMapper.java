package com.example.lab1.Mapper;

import com.example.lab1.DTO.TagRequestTo;
import com.example.lab1.DTO.TagResponseTo;
import com.example.lab1.Model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag tagRequestToTag(TagRequestTo tagRequestTo);

    TagResponseTo tagToTagResponse(Tag tag);
}
