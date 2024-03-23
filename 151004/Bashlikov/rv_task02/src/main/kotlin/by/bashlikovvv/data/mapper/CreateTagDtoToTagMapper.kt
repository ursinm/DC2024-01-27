package by.bashlikovvv.data.mapper

import by.bashlikovvv.api.dto.request.CreateTagDto
import by.bashlikovvv.data.local.model.TagEntity
import by.bashlikovvv.util.IMapper

class CreateTagDtoToTagMapper(
    private val id: Long? = null
) : IMapper<CreateTagDto, TagEntity> {
    override fun mapFromEntity(entity: CreateTagDto): TagEntity {
        return TagEntity(
            id = id ?: 0,
            name = entity.name
        )
    }

    override fun mapToEntity(domain: TagEntity): CreateTagDto {
        return CreateTagDto(
            name = domain.name
        )
    }
}