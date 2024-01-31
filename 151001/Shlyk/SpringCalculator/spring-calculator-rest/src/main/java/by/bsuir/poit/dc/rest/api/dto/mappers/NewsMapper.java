package by.bsuir.poit.dc.rest.api.dto.mappers;

import by.bsuir.poit.dc.rest.api.dto.mappers.config.CentralMapperConfig;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNewsDto;
import by.bsuir.poit.dc.rest.api.dto.response.NewsDto;
import by.bsuir.poit.dc.rest.api.dto.response.UserDto;
import by.bsuir.poit.dc.rest.dao.UserRepository;
import by.bsuir.poit.dc.rest.model.News;
import by.bsuir.poit.dc.rest.model.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {CentralMapperConfig.class})
public abstract class NewsMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Mapping(target = "author", source = "authorId", qualifiedByName = "getUserRef")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "notes", ignore = true)
    public abstract News toEntity(UpdateNewsDto dto);

    @Mapping(target = "author", source = "author", qualifiedByName = "mapUser")
    public abstract NewsDto toDto(News news);

    @Mapping(target = "author", ignore = true)//author cannot be changed
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "notes", ignore = true)
    public abstract News partialUpdate(@MappingTarget News news, UpdateNewsDto dto);

    @Named("getUserRef")
    protected User getUserRef(long userId) {
	return userRepository.getReferenceById(userId);
    }

    @Named("mapUser")
    protected UserDto mapUser(User user) {
	return userMapper.toDto(user);
    }
}