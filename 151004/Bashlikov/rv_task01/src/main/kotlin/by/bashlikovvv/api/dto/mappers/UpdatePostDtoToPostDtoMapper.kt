package by.bashlikovvv.api.dto.mappers

import by.bashlikovvv.api.dto.request.UpdatePostDto
import by.bashlikovvv.api.dto.response.PostDto
import by.bashlikovvv.util.IMapper

class UpdatePostDtoToPostDtoMapper : IMapper<UpdatePostDto, PostDto> {
    override fun mapFromEntity(entity: UpdatePostDto): PostDto {
        return PostDto(
            id = entity.id,
            tweetId = entity.tweetId,
            content = entity.content
        )
    }

    override fun mapToEntity(domain: PostDto): UpdatePostDto {
        return UpdatePostDto(
            id = domain.id,
            tweetId = domain.tweetId,
            content = domain.content
        )
    }
}