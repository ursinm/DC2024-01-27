package com.example.publicator.mapper;

import com.example.publicator.dto.LabelRequestTo;
import com.example.publicator.dto.LabelResponseTo;
import com.example.publicator.model.Label;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LabelMapper {
    Label labelRequestToLabel(LabelRequestTo labelRequestTo);

    LabelResponseTo labelToLabelResponse(Label label);
}
