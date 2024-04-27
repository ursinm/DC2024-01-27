package com.example.lab1.Mapper;

import com.example.lab1.DTO.CreatorRequestTo;
import com.example.lab1.DTO.CreatorResponseTo;
import com.example.lab1.Model.Creator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreatorMapper {
    Creator creatorRequestToCreator(CreatorRequestTo creatorRequestTo);

    CreatorResponseTo creatorToCreatorResponse(Creator creator);
}
