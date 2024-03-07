package by.bashlikovvv.api.dto.request

import by.bashlikovvv.util.inRange
import kotlinx.serialization.Serializable

@Serializable
data class CreatePostDto @Throws(IllegalStateException::class) constructor(
    val id: Long? = null,
    val tweetId: Long,
    val content: String
) {
    init {
        if (!content.inRange(2, 2048)) { throw IllegalStateException() }
    }

}