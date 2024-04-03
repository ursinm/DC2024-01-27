package by.bsuir.newsapi.service.mapper;

import by.bsuir.newsapi.model.entity.Comment;
import by.bsuir.newsapi.model.request.CommentRequestTo;
import by.bsuir.newsapi.model.response.CommentResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface CommentMapper {
    @Mapping(target = "newsId", source = "news.id")
    CommentResponseTo getResponseTo(Comment comment);

    List<CommentResponseTo> getListResponseTo(Iterable<Comment> comments);

    @Mapping(target = "news", source = "newsId", qualifiedByName = "newsIdToNewsRef")
    Comment getComment(CommentRequestTo commentRequestTo);
}
