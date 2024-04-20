package com.example.publicator.mapper;

import com.example.publicator.dto.LabelRequestTo;
import com.example.publicator.dto.LabelResponseTo;
import com.example.publicator.model.Label;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = LabelMapper.class)
public interface LabelListMapper {
    List<Label> toLabelList(List<LabelRequestTo> list);

    List<LabelResponseTo> toLabelResponseList(List<Label> list);
}
