package by.bashlikovvv.api.controller

import by.bashlikovvv.api.controller.routing.editorsRouting
import by.bashlikovvv.api.controller.routing.postsRouting
import by.bashlikovvv.api.controller.routing.tagsRouting
import by.bashlikovvv.api.controller.routing.tweetsRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        route("/api/v1.0") {
            editorsRouting()
            tweetsRouting()
            postsRouting()
            tagsRouting()
        }
    }
}