package by.bashlikovvv.api.dto.mappers

import by.bashlikovvv.api.dto.request.UpdateTweetDto
import by.bashlikovvv.api.dto.response.TweetDto
import by.bashlikovvv.util.IMapper
import java.sql.Timestamp

class UpdateTweetDtoToTweetDtoMapper(
    private val tweetDto: TweetDto,
    private val modified: Timestamp
) : IMapper<UpdateTweetDto, TweetDto> {
    override fun mapFromEntity(entity: UpdateTweetDto): TweetDto {
        return TweetDto(
            id = tweetDto.id,
            editorId = entity.editorId,
            title = entity.title,
            content = entity.content.toString(),
            created = tweetDto.created,
            modified = modified
        )
    }

    override fun mapToEntity(domain: TweetDto): UpdateTweetDto {
        return UpdateTweetDto(
            id = domain.id,
            editorId = domain.editorId,
            title = domain.title,
            content = domain.content
        )
    }
}