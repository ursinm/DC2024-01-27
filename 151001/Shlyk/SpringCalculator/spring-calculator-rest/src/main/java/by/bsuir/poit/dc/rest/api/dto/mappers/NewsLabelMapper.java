package by.bsuir.poit.dc.rest.api.dto.mappers;

import by.bsuir.poit.dc.rest.api.dto.mappers.config.CentralMapperConfig;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNewsLabelDto;
import by.bsuir.poit.dc.rest.model.NewsLabel;
import by.bsuir.poit.dc.rest.model.NewsLabelId;
import org.mapstruct.*;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    config = CentralMapperConfig.class
)
public abstract class NewsLabelMapper {
    @Mapping(target = "id", expression = "java(mapId(newsId, dto.labelId()))")
    @Mapping(target = "news", source = "newsId", qualifiedByName = "getNewsRef")
    @Mapping(target = "label", source = "dto.labelId", qualifiedByName = "getLabelRef")
    public abstract NewsLabel toEntity(long newsId, UpdateNewsLabelDto dto);

    @Named("mapId")
    protected NewsLabelId mapId(long newsId, long labelId) {
	return NewsLabelId.builder()
		   .newsId(newsId)
		   .labelId(labelId)
		   .build();
    }

}