package com.example.restapplication.mappers;

import com.example.restapplication.dto.TagRequestTo;
import com.example.restapplication.dto.TagResponseTo;
import com.example.restapplication.entites.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag toTag(TagRequestTo tag);
    TagResponseTo toTagResponse(Tag tag);
}
