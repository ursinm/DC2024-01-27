package by.bashlikovvv.api.dto.request

import by.bashlikovvv.util.inRange
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTweetDto @Throws(IllegalStateException::class) constructor(
    val id: Long? = null,
    val editorId: Long,
    val title: String,
    val content: String? = null,
    val name: String? = null
) {

    init {
        if (!title.inRange(2, 64)) { throw IllegalStateException() }
        if (content?.inRange(4, 2048) == false) { throw IllegalStateException() }
    }

}