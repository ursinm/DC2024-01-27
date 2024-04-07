package by.rusakovich.newsdistributedsystem.model.dto.mapper.impl;

import by.rusakovich.newsdistributedsystem.model.dto.author.AuthorResponseTO;
import by.rusakovich.newsdistributedsystem.model.dto.label.LabelRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.label.LabelResponseTO;
import by.rusakovich.newsdistributedsystem.model.dto.mapper.ConversionError;
import by.rusakovich.newsdistributedsystem.model.dto.mapper.EntityMapper;
import by.rusakovich.newsdistributedsystem.model.entity.impl.Author;
import by.rusakovich.newsdistributedsystem.model.entity.impl.Label;
import by.rusakovich.newsdistributedsystem.model.entity.impl.jpa.JpaLabel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LabelMapper extends EntityMapper<Long, JpaLabel, LabelRequestTO, LabelResponseTO> {
    @Override
    JpaLabel mapToEntity(LabelRequestTO request) throws ConversionError;
    @Override
    LabelResponseTO mapToResponse(JpaLabel entity)throws ConversionError;

}
