package by.rusakovich.publisher.model.dto.mapper.impl;

import by.rusakovich.publisher.model.dto.author.AuthorRequestTO;
import by.rusakovich.publisher.model.dto.author.AuthorResponseTO;
import by.rusakovich.publisher.model.dto.mapper.ConversionError;
import by.rusakovich.publisher.model.dto.mapper.EntityMapper;
import by.rusakovich.publisher.model.entity.impl.jpa.JpaAuthor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper extends EntityMapper<Long, JpaAuthor, AuthorRequestTO, AuthorResponseTO> {
    @Override
    JpaAuthor mapToEntity(AuthorRequestTO request) throws ConversionError;
    @Override
    AuthorResponseTO mapToResponse(JpaAuthor entity) throws ConversionError;

    @Mapping(target = "password", expression = "java(null)")
    JpaAuthor toEntity(AuthorResponseTO response) throws ConversionError;
}
