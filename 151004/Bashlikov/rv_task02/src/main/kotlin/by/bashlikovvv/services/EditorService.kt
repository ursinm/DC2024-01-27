package by.bashlikovvv.services

import by.bashlikovvv.api.dto.request.CreateEditorDto
import by.bashlikovvv.api.dto.request.UpdateEditorDto
import by.bashlikovvv.api.dto.response.EditorDto

interface EditorService {

    suspend fun create(createEditorDto: CreateEditorDto): EditorDto?

    suspend fun update(editorId: Long, updateEditorDto: UpdateEditorDto): EditorDto?

    suspend fun getById(editorId: Long): EditorDto?

    suspend fun getAll(): List<EditorDto?>

    suspend fun delete(editorId: Long): Boolean

}