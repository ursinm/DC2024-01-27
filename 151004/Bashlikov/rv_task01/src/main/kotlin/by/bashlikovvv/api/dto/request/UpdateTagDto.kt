package by.bashlikovvv.api.dto.request

import by.bashlikovvv.util.inRange
import kotlinx.serialization.Serializable
import kotlin.jvm.Throws

@Serializable
class UpdateTagDto @Throws(IllegalStateException::class) constructor(
    val id: Long,
    val name: String
) {

    init {
        if (!name.inRange(2, 32)) { throw IllegalStateException() }
    }

}