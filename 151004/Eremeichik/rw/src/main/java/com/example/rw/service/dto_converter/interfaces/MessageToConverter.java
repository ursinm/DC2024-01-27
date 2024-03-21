package com.example.rw.service.dto_converter.interfaces;

import com.example.rw.exception.model.dto_converting.ToConvertingException;
import com.example.rw.model.dto.message.MessageRequestTo;
import com.example.rw.model.dto.message.MessageResponseTo;
import com.example.rw.model.entity.implementations.Message;
import com.example.rw.model.entity.implementations.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageToConverter extends ToConverter<Message, MessageRequestTo, MessageResponseTo> {

    @Mapping(target = "news", expression = "java(idToNews(requestTo.getNewsId()))")
    Message convertToEntity(MessageRequestTo requestTo) throws ToConvertingException;

    default News idToNews(Long newsId){
        News news = new News();
        news.setId(newsId);
        return news;
    }

    @Mapping(target = "newsId", expression = "java(entity.getNews()!=null?entity.getNews().getId():null)")
    MessageResponseTo convertToDto(Message entity) throws ToConvertingException;
}
