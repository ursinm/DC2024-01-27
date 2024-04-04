package by.bashlikovvv.api.dto.mappers

import by.bashlikovvv.api.dto.request.CreatePostDto
import by.bashlikovvv.api.dto.response.PostDto
import by.bashlikovvv.util.IMapper

class CreatePostDtoToPostDtoMapper(
    private val id: Long
) : IMapper<CreatePostDto, PostDto> {
    override fun mapFromEntity(entity: CreatePostDto): PostDto {
        return PostDto(
            id = id,
            tweetId = entity.tweetId,
            content = entity.content
        )
    }

    override fun mapToEntity(domain: PostDto): CreatePostDto {
        return CreatePostDto(
            id = domain.id,
            tweetId = domain.tweetId,
            content = domain.content
        )
    }
}