package by.bashlikovvv.domain.model

import kotlinx.serialization.Serializable

@Serializable
class Editor(
    val id: Long,
    val login: String,
    val password: String,
    val firstname: String,
    val lastname: String
)