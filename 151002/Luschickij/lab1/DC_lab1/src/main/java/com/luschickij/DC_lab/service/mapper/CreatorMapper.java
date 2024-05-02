package com.luschickij.DC_lab.service.mapper;

import com.luschickij.DC_lab.model.entity.Creator;
import com.luschickij.DC_lab.model.request.CreatorRequestTo;
import com.luschickij.DC_lab.model.response.CreatorResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface CreatorMapper {
    @Mapping(source = "firstName", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    CreatorResponseTo getResponse(Creator creator);

    @Mapping(source = "firstName", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    List<CreatorResponseTo> getListResponse(Iterable<Creator> creators);

    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    @Mapping(target = "news", ignore = true)
    Creator getCreator(CreatorRequestTo creatorRequestTo);
}
