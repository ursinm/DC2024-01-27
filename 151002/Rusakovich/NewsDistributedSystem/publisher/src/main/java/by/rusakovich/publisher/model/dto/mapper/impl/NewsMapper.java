package by.rusakovich.publisher.model.dto.mapper.impl;

import by.rusakovich.publisher.dao.IEntityRepository;
import by.rusakovich.publisher.model.dto.mapper.ConversionError;
import by.rusakovich.publisher.model.dto.mapper.EntityMapper;
import by.rusakovich.publisher.model.dto.news.NewsRequestTO;
import by.rusakovich.publisher.model.dto.news.NewsResponseTO;
import by.rusakovich.publisher.model.entity.impl.jpa.JpaAuthor;
import by.rusakovich.publisher.model.entity.impl.jpa.JpaNews;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class NewsMapper implements EntityMapper<Long, JpaNews, NewsRequestTO, NewsResponseTO> {
    @Autowired
    protected IEntityRepository<Long, JpaAuthor> authorRepository;


    @Override
    @Mapping(target = "author", expression = "java(getAuthorById(request.authorId()))")
    public abstract JpaNews mapToEntity(NewsRequestTO request)throws ConversionError;

    JpaAuthor getAuthorById(Long id){
        JpaAuthor temp = new JpaAuthor();
        temp.setId(id);
        return authorRepository.readById(id).orElse(temp);
    }

    @Override
    @Mapping(target = "authorId", expression = "java(getAuthorId(entity))")
    public abstract NewsResponseTO mapToResponse(JpaNews entity)throws ConversionError;

    Long getAuthorId(JpaNews entity){
        if(entity == null){
            return null;
        }else{
            return entity.getAuthor() != null ? entity.getAuthor().getId() : null;
        }
    }

    @Mapping(target = "creation", expression = "java(null)")
    @Mapping(target = "modification", expression = "java(null)")
    public abstract JpaNews toEntity(NewsResponseTO response) throws ConversionError;
}

