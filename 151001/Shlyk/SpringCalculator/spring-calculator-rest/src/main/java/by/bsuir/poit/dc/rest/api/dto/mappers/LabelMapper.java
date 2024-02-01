package by.bsuir.poit.dc.rest.api.dto.mappers;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateLabelDto;
import by.bsuir.poit.dc.rest.api.dto.response.LabelDto;
import by.bsuir.poit.dc.rest.model.Label;
import org.mapstruct.*;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class LabelMapper {
    @Mapping(target = "id", ignore = true)
    public abstract Label toEntity(UpdateLabelDto dto);

    @Mapping(target = "id", ignore = true)
    public abstract Label partialUpdate(@MappingTarget Label label, UpdateLabelDto dto);

    @Named("toDto")
    public abstract LabelDto toDto(Label label);

    @IterableMapping(qualifiedByName = "toDto")
    public abstract List<LabelDto> toDtoList(List<Label> labels);
}