package by.bashlikovvv.services

import by.bashlikovvv.api.dto.request.CreateTagDto
import by.bashlikovvv.api.dto.request.UpdateTagDto
import by.bashlikovvv.api.dto.response.TagDto

interface TagService {

    fun create(createTagDto: CreateTagDto): TagDto?

    fun getAll(): List<TagDto?>

    fun getById(tagId: Long): TagDto?

    fun update(tagId: Long, updateTagDto: UpdateTagDto): TagDto?

    fun delete(tagId: Long): Boolean

}