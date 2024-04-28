package com.example.lab1.mapper;

import com.example.lab1.dto.CreatorRequestTo;
import com.example.lab1.dto.CreatorResponseTo;
import com.example.lab1.model.Creator;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CreatorMapper.class)
public interface CreatorListMapper {
    List<Creator> toCreatorList(List<CreatorRequestTo> creators);

    List<CreatorResponseTo> toCreatorResponseList(List<Creator> creators);
}
