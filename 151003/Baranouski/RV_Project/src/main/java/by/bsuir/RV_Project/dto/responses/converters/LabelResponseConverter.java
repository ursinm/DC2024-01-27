package by.bsuir.RV_Project.dto.responses.converters;

import by.bsuir.RV_Project.domain.Label;
import by.bsuir.RV_Project.dto.responses.LabelResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LabelResponseConverter {
    LabelResponseDto toDto(Label label);
}
