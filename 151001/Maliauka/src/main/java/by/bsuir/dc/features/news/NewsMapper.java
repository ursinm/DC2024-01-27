package by.bsuir.dc.features.news;

import by.bsuir.dc.features.news.dto.NewsRequestDto;
import by.bsuir.dc.features.news.dto.NewsResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    NewsResponseDto toDto(News news);

    List<NewsResponseDto> toDtoList(List<News> news);

    News toEntity(NewsRequestDto newsRequestDto);
}
