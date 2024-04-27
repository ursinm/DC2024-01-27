package by.bashlikovvv.data.mapper

import by.bashlikovvv.api.dto.request.UpdateTagDto
import by.bashlikovvv.data.local.model.TagEntity
import by.bashlikovvv.util.IMapper

class UpdateTagDtoToTagMapper : IMapper<UpdateTagDto, TagEntity> {
    override fun mapFromEntity(entity: UpdateTagDto): TagEntity {
        return TagEntity(
            id = entity.id,
            name = entity.name
        )
    }

    override fun mapToEntity(domain: TagEntity): UpdateTagDto {
        return UpdateTagDto(
            id = domain.id,
            name = domain.name
        )
    }
}