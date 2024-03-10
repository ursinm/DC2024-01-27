package by.bashlikovvv.data.mapper

import by.bashlikovvv.api.dto.request.UpdatePostDto
import by.bashlikovvv.data.local.model.PostEntity
import by.bashlikovvv.util.IMapper

class UpdatePostDtoToPostMapper : IMapper<UpdatePostDto, PostEntity> {
    override fun mapFromEntity(entity: UpdatePostDto): PostEntity {
        return PostEntity(
            id = entity.id,
            tweetId = entity.tweetId,
            content = entity.content
        )
    }

    override fun mapToEntity(domain: PostEntity): UpdatePostDto {
        return UpdatePostDto(
            id = domain.id,
            tweetId = domain.tweetId,
            content = domain.content
        )
    }
}