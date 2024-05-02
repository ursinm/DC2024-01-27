package com.example.publisher.service.mapper;
import com.example.publisher.model.entity.Label;
import com.example.publisher.model.request.LabelRequestTo;
import com.example.publisher.model.response.LabelResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LabelMapper {
    LabelResponseTo getResponse(Label label);
    List<LabelResponseTo> getListResponse(Iterable<Label> labels);
    Label getLabel(LabelRequestTo labelRequestTo);
}
