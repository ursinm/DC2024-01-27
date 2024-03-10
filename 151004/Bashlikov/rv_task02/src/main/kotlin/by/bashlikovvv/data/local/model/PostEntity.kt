package by.bashlikovvv.data.local.model

import kotlinx.serialization.Serializable

@Serializable
data class PostEntity(
    val id: Long,
    val tweetId: Long,
    val content: String
)