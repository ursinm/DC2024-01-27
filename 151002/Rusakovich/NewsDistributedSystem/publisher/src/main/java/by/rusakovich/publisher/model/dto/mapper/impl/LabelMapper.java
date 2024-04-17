package by.rusakovich.publisher.model.dto.mapper.impl;

import by.rusakovich.publisher.model.dto.label.LabelRequestTO;
import by.rusakovich.publisher.model.dto.label.LabelResponseTO;
import by.rusakovich.publisher.model.dto.mapper.ConversionError;
import by.rusakovich.publisher.model.dto.mapper.EntityMapper;
import by.rusakovich.publisher.model.entity.impl.jpa.JpaLabel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LabelMapper extends EntityMapper<Long, JpaLabel, LabelRequestTO, LabelResponseTO> {
    @Override
    JpaLabel mapToEntity(LabelRequestTO request) throws ConversionError;
    @Override
    LabelResponseTO mapToResponse(JpaLabel entity)throws ConversionError;

}
