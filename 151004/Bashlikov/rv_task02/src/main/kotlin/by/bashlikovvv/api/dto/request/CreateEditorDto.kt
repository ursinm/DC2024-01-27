package by.bashlikovvv.api.dto.request

import by.bashlikovvv.util.inRange
import kotlinx.serialization.Serializable
import kotlin.jvm.Throws

@Serializable
data class CreateEditorDto @Throws(IllegalArgumentException::class) constructor(
    val login: String,
    val password: String,
    val firstname: String,
    val lastname: String
) {

    init {
        require(login.inRange(2, 64))
        require(password.inRange(8, 128))
        require(firstname.inRange(2, 64))
        require(lastname.inRange(2, 64))
    }

}