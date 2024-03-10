package by.bsuir.dc.service.mapper;

import by.bsuir.dc.dto.request.TweetRequestDto;
import by.bsuir.dc.dto.response.TweetResponseDto;
import by.bsuir.dc.entity.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TweetMapper {
    TweetResponseDto toDto(Tweet entity);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Tweet toEntity(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> toDto(Iterable<Tweet> entities);

    List<Tweet> toEntity(Iterable<TweetRequestDto> entities);
}
