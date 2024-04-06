package by.bsuir.poit.dc.rest.api.dto.mappers;

import by.bsuir.poit.dc.rest.api.dto.mappers.config.CentralMapperConfig;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNewsDto;
import by.bsuir.poit.dc.rest.api.dto.response.NewsDto;
import by.bsuir.poit.dc.rest.api.dto.response.UserDto;
import by.bsuir.poit.dc.rest.model.News;
import by.bsuir.poit.dc.rest.model.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    config = CentralMapperConfig.class)
public abstract class NewsMapper {
    @Autowired
    private UserMapper userMapper;

    @Mapping(target = "labels", ignore = true)
    @Mapping(target = "user", source = "userId", qualifiedByName = "getUserRef")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    public abstract News toEntity(UpdateNewsDto dto);

    @Mapping(target = "userId", source = "user.id")
    @Named("toDto")
    public abstract NewsDto toDto(News news);

    @Mapping(target = "labels", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "user",
	source = "userId",
	qualifiedByName = "getUserRef")//author cannot be changed
    public abstract News partialUpdate(
	@MappingTarget News news,
	UpdateNewsDto dto);


    @Named("mapUser")
    protected UserDto mapUser(User user) {
	return userMapper.toDto(user);
    }

    @IterableMapping(qualifiedByName = "toDto")
    public abstract List<NewsDto> toDtoList(List<News> list);
}