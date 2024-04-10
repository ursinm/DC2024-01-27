package com.example.lab1.Mapper;

import com.example.lab1.DTO.CreatorRequestTo;
import com.example.lab1.DTO.CreatorResponseTo;
import com.example.lab1.Model.Creator;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CreatorMapper.class)
public interface CreatorListMapper {
    List<Creator> toCreatorList(List<CreatorRequestTo> creators);

    List<CreatorResponseTo> toCreatorResponseList(List<Creator> creators);
}
