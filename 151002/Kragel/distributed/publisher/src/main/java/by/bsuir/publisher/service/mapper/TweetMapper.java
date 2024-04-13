package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.dto.request.TweetRequestDto;
import by.bsuir.publisher.dto.response.TweetResponseDto;
import by.bsuir.publisher.model.Tweet;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface TweetMapper {
    @Mapping(target = "creatorId", source = "creator.id")
    TweetResponseDto toDto(Tweet entity);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "creator", source = "creatorId", qualifiedByName = "creatorIdToCreatorRef")
    Tweet toEntity(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> toDto(Collection<Tweet> entities);

    List<Tweet> toEntity(Collection<TweetRequestDto> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creator", source = "creatorId", qualifiedByName = "creatorIdToCreatorRef")
    Tweet partialUpdate(TweetRequestDto tweetRequestDto, @MappingTarget Tweet tweet);
}
