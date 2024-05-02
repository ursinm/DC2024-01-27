package com.example.lab2.mapper;

import com.example.lab2.dto.CreatorRequestTo;
import com.example.lab2.dto.CreatorResponseTo;
import com.example.lab2.model.Creator;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CreatorMapper.class)
public interface CreatorListMapper {
    List<Creator> toCreatorList(List<CreatorRequestTo> creators);

    List<CreatorResponseTo> toCreatorResponseList(List<Creator> creators);
}
