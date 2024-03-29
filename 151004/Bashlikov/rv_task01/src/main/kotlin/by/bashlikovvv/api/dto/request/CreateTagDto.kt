package by.bashlikovvv.api.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateTagDto(
    val name: String
)