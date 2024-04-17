package by.rusakovich.publisher.model.dto.mapper.impl;

import by.rusakovich.publisher.dao.IEntityRepository;
import by.rusakovich.publisher.model.dto.mapper.ConversionError;
import by.rusakovich.publisher.model.dto.mapper.EntityMapper;
import by.rusakovich.publisher.model.dto.note.NoteRequestTO;
import by.rusakovich.publisher.model.dto.note.NoteResponseTO;
import by.rusakovich.publisher.model.entity.impl.jpa.JpaNews;
import by.rusakovich.publisher.model.entity.impl.jpa.JpaNote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class NoteMapper implements EntityMapper<Long, JpaNote, NoteRequestTO, NoteResponseTO> {
    @Autowired
    protected IEntityRepository<Long, JpaNews> jpaNoteRepository;

    @Override
    @Mapping(target = "news", expression = "java(getNewsById(request.newsId()))")
    public abstract JpaNote mapToEntity(NoteRequestTO request)throws ConversionError;

    JpaNews getNewsById(Long id){
        JpaNews temp = new JpaNews();
        temp.setId(id);
        return jpaNoteRepository.readById(id).orElse(temp);
    }

    Long getNewsId(JpaNote entity){
        return entity.getNews() != null ? entity.getNews().getId() : null;
    }
    @Override
    @Mapping(target = "newsId", expression = "java(getNewsId(entity))")
    public abstract NoteResponseTO mapToResponse(JpaNote entity)throws ConversionError;
}
