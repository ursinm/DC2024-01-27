package by.bashlikovvv.data.mapper

import by.bashlikovvv.api.dto.request.CreatePostDto
import by.bashlikovvv.data.local.model.PostEntity
import by.bashlikovvv.util.IMapper

class CreatePostDtoToPostMapper(
    private val id: Long? = null
) : IMapper<CreatePostDto, PostEntity> {
    override fun mapFromEntity(entity: CreatePostDto): PostEntity {
        return PostEntity(
            id = id ?: 0,
            tweetId = entity.tweetId,
            content = entity.content
        )
    }

    override fun mapToEntity(domain: PostEntity): CreatePostDto {
        return CreatePostDto(
            id = domain.id,
            tweetId = domain.tweetId,
            content = domain.content
        )
    }
}