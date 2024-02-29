package by.bashlikovvv.services.impl

import by.bashlikovvv.api.dto.mappers.CreateTagDtoToTagDtoMapper
import by.bashlikovvv.api.dto.mappers.UpdateTagDtoToTagDtoMapper
import by.bashlikovvv.api.dto.request.CreateTagDto
import by.bashlikovvv.api.dto.request.UpdateTagDto
import by.bashlikovvv.api.dto.response.TagDto
import by.bashlikovvv.domain.repository.ITagsRepository
import by.bashlikovvv.services.TagService

class TagServiceImpl(
    private val tagsRepository: ITagsRepository
) : TagService {
    override fun create(createTagDto: CreateTagDto): TagDto? {
        val lastItemId = if (tagsRepository.data.isEmpty()) {
            -1
        } else {
            tagsRepository.getLastItem()?.id ?: return null
        }
        val savedEntity = tagsRepository.addItem(
            id = lastItemId + 1,
            item = CreateTagDtoToTagDtoMapper(
                id = lastItemId + 1,
                name = createTagDto.name
            ).mapFromEntity(createTagDto)
        )

        return savedEntity
    }

    override fun getAll(): List<TagDto?> {
        return tagsRepository.data.map { it.second }
    }

    override fun getById(tagId: Long): TagDto? {
        return tagsRepository.getItemById(tagId)?.second
    }

    override fun update(tagId: Long, updateTagDto: UpdateTagDto): TagDto? {
        val savedEntity = tagsRepository.addItem(
            id = tagId,
            item = UpdateTagDtoToTagDtoMapper(updateTagDto.name).mapFromEntity(updateTagDto)
        )

        return savedEntity
    }

    override fun delete(tagId: Long): Boolean {
        return tagsRepository.removeItem(tagId)
    }
}