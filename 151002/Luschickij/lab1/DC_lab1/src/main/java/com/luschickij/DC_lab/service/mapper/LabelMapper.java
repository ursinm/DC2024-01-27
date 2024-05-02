package com.luschickij.DC_lab.service.mapper;

import com.luschickij.DC_lab.model.entity.Label;
import com.luschickij.DC_lab.model.request.LabelRequestTo;
import com.luschickij.DC_lab.model.response.LabelResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LabelMapper {
    LabelResponseTo getResponse(Label label);
    List<LabelResponseTo> getListResponse(Iterable<Label> labels);
    @Mapping(target = "news", ignore = true)
    Label getLabel(LabelRequestTo labelRequestTo);
}
