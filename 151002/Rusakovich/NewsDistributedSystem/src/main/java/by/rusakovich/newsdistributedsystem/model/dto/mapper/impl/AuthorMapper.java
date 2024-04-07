package by.rusakovich.newsdistributedsystem.model.dto.mapper.impl;

import by.rusakovich.newsdistributedsystem.model.dto.author.AuthorRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.author.AuthorResponseTO;
import by.rusakovich.newsdistributedsystem.model.dto.mapper.ConversionError;
import by.rusakovich.newsdistributedsystem.model.dto.mapper.EntityMapper;
import by.rusakovich.newsdistributedsystem.model.entity.impl.Author;
import by.rusakovich.newsdistributedsystem.model.entity.impl.jpa.JpaAuthor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper extends EntityMapper<Long, JpaAuthor, AuthorRequestTO, AuthorResponseTO> {
    @Override
    JpaAuthor mapToEntity(AuthorRequestTO request) throws ConversionError;
    @Override
    AuthorResponseTO mapToResponse(JpaAuthor entity) throws ConversionError;

    @Mapping(target = "password", expression = "java(null)")
    JpaAuthor toEntity(AuthorResponseTO response) throws ConversionError;
}
