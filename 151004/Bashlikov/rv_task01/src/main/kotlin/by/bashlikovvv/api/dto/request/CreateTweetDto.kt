package by.bashlikovvv.api.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateTweetDto(
    val editorId: Long,
    val title: String,
    val content: String? = null,
    val name: String? = null
)