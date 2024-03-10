package by.bashlikovvv.api.dto.request

import by.bashlikovvv.util.inRange
import kotlinx.serialization.Serializable

@Serializable
class UpdatePostDto @Throws(IllegalStateException::class) constructor(
    val id: Long,
    val tweetId: Long,
    val content: String
) {
    init {
        if (!content.inRange(2, 2048)) { throw IllegalStateException() }
    }

}