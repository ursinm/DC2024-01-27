package by.bsuir.newsapi.service.mapper;

import by.bsuir.newsapi.model.entity.Comment;
import by.bsuir.newsapi.model.entity.News;
import by.bsuir.newsapi.model.request.CommentRequestTo;
import by.bsuir.newsapi.model.request.NewsRequestTo;
import by.bsuir.newsapi.model.response.CommentResponseTo;
import by.bsuir.newsapi.model.response.NewsResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = NewsMapper.class)
public interface CommentMapper {
    CommentResponseTo getResponseTo(Comment comment);

    List<CommentResponseTo> getListResponseTo(Iterable<Comment> comments);

    Comment getComment(CommentRequestTo commentRequestTo);
}
