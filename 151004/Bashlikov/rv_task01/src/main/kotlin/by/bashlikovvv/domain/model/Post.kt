package by.bashlikovvv.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    val tweetId: Long,
    val content: String
)