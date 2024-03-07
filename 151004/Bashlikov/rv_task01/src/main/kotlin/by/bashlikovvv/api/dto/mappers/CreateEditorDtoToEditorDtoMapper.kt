package by.bashlikovvv.api.dto.mappers

import by.bashlikovvv.api.dto.request.CreateEditorDto
import by.bashlikovvv.api.dto.response.EditorDto
import by.bashlikovvv.util.IMapper

class CreateEditorDtoToEditorDtoMapper(
    private val id: Long
) : IMapper<CreateEditorDto, EditorDto> {
    @Throws(IllegalStateException::class)
    override fun mapFromEntity(entity: CreateEditorDto): EditorDto {
        return EditorDto(
            id = id,
            login = entity.login,
            password = entity.password,
            firstname = entity.firstname,
            lastname = entity.lastname
        )
    }

    @Throws(IllegalStateException::class)
    override fun mapToEntity(domain: EditorDto): CreateEditorDto {
        return CreateEditorDto(
            login = domain.login,
            password = domain.password,
            firstname = domain.firstname,
            lastname = domain.lastname
        )
    }
}