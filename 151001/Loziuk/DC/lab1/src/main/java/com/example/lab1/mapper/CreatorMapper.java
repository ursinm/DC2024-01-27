package com.example.lab1.mapper;

import com.example.lab1.dto.CreatorRequestTo;
import com.example.lab1.dto.CreatorResponseTo;
import com.example.lab1.model.Creator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreatorMapper {
    Creator creatorRequestToCreator(CreatorRequestTo creatorRequestTo);

    CreatorResponseTo creatorToCreatorResponse(Creator creator);
}
