package com.danilovfa.controller

import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.apiController() {
    route("api/v1.0") {
        postController(service = get())
        stickerController(service = get())
        storyController(service = get())
        userController(service = get())
    }
}