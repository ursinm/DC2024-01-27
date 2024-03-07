package by.bashlikovvv.api.dto.mappers

import by.bashlikovvv.api.dto.request.CreateTagDto
import by.bashlikovvv.api.dto.response.TagDto
import by.bashlikovvv.util.IMapper

class CreateTagDtoToTagDtoMapper(
    private val id: Long,
    private val name: String
) : IMapper<CreateTagDto, TagDto> {
    override fun mapFromEntity(entity: CreateTagDto): TagDto {
        return TagDto(
            id = id,
            name = entity.name
        )
    }

    override fun mapToEntity(domain: TagDto): CreateTagDto {
        return CreateTagDto(name = name)
    }
}