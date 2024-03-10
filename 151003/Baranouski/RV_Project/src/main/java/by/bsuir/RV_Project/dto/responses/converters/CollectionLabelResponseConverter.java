package by.bsuir.RV_Project.dto.responses.converters;

import by.bsuir.RV_Project.domain.Label;
import by.bsuir.RV_Project.dto.responses.LabelResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = LabelResponseConverter.class)
public interface CollectionLabelResponseConverter {
    List<LabelResponseDto> toListDto(List<Label> labels);
}
