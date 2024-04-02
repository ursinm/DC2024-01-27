package by.bashlikovvv.api.dto.mappers

import by.bashlikovvv.api.dto.request.CreateTweetDto
import by.bashlikovvv.api.dto.response.TweetDto
import by.bashlikovvv.util.IMapper
import java.sql.Timestamp

class CreateTweetDtoToTweetDtoMapper(
    private val id: Long,
    private val created: Timestamp
) : IMapper<CreateTweetDto, TweetDto> {
    override fun mapFromEntity(entity: CreateTweetDto): TweetDto {
        return TweetDto(
            id = id,
            editorId = entity.editorId,
            title = entity.title,
            content = entity.content.toString(),
            created = created,
            modified = created
        )
    }

    override fun mapToEntity(domain: TweetDto): CreateTweetDto {
        return CreateTweetDto(
            editorId = domain.editorId,
            title = domain.title,
            content = domain.content,
        )
    }
}