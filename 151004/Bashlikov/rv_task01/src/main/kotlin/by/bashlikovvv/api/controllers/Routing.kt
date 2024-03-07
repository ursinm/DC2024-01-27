package by.bashlikovvv.api.controllers

import by.bashlikovvv.api.controllers.routings.editorsRouting
import by.bashlikovvv.api.controllers.routings.postsRouting
import by.bashlikovvv.api.controllers.routings.tagsRouting
import by.bashlikovvv.api.controllers.routings.tweetsRouting
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