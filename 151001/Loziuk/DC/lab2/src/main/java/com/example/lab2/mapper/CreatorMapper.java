package com.example.lab2.mapper;

import com.example.lab2.dto.CreatorRequestTo;
import com.example.lab2.dto.CreatorResponseTo;
import com.example.lab2.model.Creator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreatorMapper {
    Creator creatorRequestToCreator(CreatorRequestTo creatorRequestTo);

    CreatorResponseTo creatorToCreatorResponse(Creator creator);
}
