package by.bsuir.RV_Project.dto.requests.converters;

import by.bsuir.RV_Project.domain.Label;
import by.bsuir.RV_Project.dto.requests.LabelRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LabelRequestConverter {
    Label fromDto(LabelRequestDto label);
}
