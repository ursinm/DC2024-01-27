package by.bsuir.poit.dc.rest.api.dto.mappers;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateUserDto;
import by.bsuir.poit.dc.rest.api.dto.response.UserDto;
import by.bsuir.poit.dc.rest.model.User;
import org.mapstruct.*;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "news", ignore = true)
    public abstract User toEntity(UpdateUserDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "news", ignore = true)
    public abstract User partialUpdate(
	@MappingTarget User user,
	UpdateUserDto dto);

    @Named("toDto")
    public abstract UserDto toDto(User user);

    @IterableMapping(qualifiedByName = "toDto")
    public abstract List<UserDto> toDtoList(List<User> users);
}