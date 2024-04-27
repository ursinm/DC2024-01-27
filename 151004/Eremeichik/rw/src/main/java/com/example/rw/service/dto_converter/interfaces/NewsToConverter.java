package com.example.rw.service.dto_converter.interfaces;

import com.example.rw.exception.model.dto_converting.ToConvertingException;
import com.example.rw.model.dto.news.NewsRequestTo;
import com.example.rw.model.dto.news.NewsResponseTo;
import com.example.rw.model.entity.implementations.News;
import com.example.rw.model.entity.implementations.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NewsToConverter extends ToConverter<News, NewsRequestTo, NewsResponseTo> {

    @Mapping(target = "user", expression = "java(idToUser(requestTo.getUserId()))")
    News convertToEntity(NewsRequestTo requestTo) throws ToConvertingException;

    default User idToUser(Long userId){
        User user = new User();
        user.setId(userId);
        return user;
    }

    @Mapping(target = "userId", expression = "java(entity.getUser()!=null?entity.getUser().getId():null)")
    NewsResponseTo convertToDto(News entity) throws ToConvertingException;
}
