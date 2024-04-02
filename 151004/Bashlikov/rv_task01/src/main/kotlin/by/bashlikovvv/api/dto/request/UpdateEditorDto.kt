package by.bashlikovvv.api.dto.request

import by.bashlikovvv.util.inRange
import kotlinx.serialization.Serializable
import kotlin.jvm.Throws

@Serializable
class UpdateEditorDto @Throws(IllegalStateException::class) constructor(
    val id: Long,
    val login: String,
    val password: String,
    val firstname: String,
    val lastname: String
) {

    init {
        if (!login.inRange(2, 64)) {
            throw IllegalStateException()
        }
        if (!password.inRange(8, 128)) {
            throw IllegalStateException()
        }
        if (!firstname.inRange(2, 64)) {
            throw IllegalStateException()
        }
        if (!lastname.inRange(2, 64)) {
            throw IllegalStateException()
        }
    }

}