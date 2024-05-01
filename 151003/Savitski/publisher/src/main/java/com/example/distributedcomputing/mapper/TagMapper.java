package com.example.distributedcomputing.mapper;

import com.example.distributedcomputing.model.entity.Tag;
import com.example.distributedcomputing.model.request.TagRequestTo;
import com.example.distributedcomputing.model.response.TagResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag dtoToEntity(TagRequestTo tagRequestTo);
    List<Tag> dtoToEntity(Iterable<Tag> tags);

    TagResponseTo entityToDto(Tag tag);

    List<TagResponseTo> entityToDto(Iterable<Tag> tags);
}
