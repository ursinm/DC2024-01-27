package by.bashlikovvv.api.dto.request

import by.bashlikovvv.util.inRange
import kotlinx.serialization.Serializable
import kotlin.jvm.Throws

@Serializable
data class UpdateTagDto @Throws(IllegalArgumentException::class) constructor(
    val id: Long,
    val name: String
) {

    init {
        require(name.inRange(2, 32))
    }

}