package by.rusakovich.newsdistributedsystem.model.dto.mapper.impl;

import by.rusakovich.newsdistributedsystem.model.dto.label.LabelRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.label.LabelResponseTO;
import by.rusakovich.newsdistributedsystem.model.dto.mapper.EntityMapper;
import by.rusakovich.newsdistributedsystem.model.entity.impl.Label;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LabelMapper extends EntityMapper<Long, Label<Long>, LabelRequestTO, LabelResponseTO> {

    Label<Long> mapToEntity(LabelRequestTO request);
    List<LabelResponseTO> mapToResponseList(Iterable<Label<Long>> entities);
    LabelResponseTO mapToResponse(Label<Long> entity);
}
