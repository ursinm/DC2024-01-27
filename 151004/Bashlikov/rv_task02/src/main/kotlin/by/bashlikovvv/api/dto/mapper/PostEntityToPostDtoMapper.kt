package by.bashlikovvv.api.dto.mapper

import by.bashlikovvv.api.dto.response.PostDto
import by.bashlikovvv.data.local.model.PostEntity
import by.bashlikovvv.util.IMapper

class PostEntityToPostDtoMapper : IMapper<PostEntity, PostDto> {
    override fun mapFromEntity(entity: PostEntity): PostDto {
        return PostDto(
            id = entity.id,
            tweetId = entity.tweetId,
            content = entity.content
        )
    }

    override fun mapToEntity(domain: PostDto): PostEntity {
        return PostEntity(
            id = domain.id,
            tweetId = domain.tweetId,
            content = domain.content
        )
    }
}