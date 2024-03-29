package com.danilovfa.controller

import com.danilovfa.model.post.PostCreateRequestTo
import com.danilovfa.model.post.PostUpdateRequestTo
import com.danilovfa.model.post.toResponse
import com.danilovfa.service.PostService
import com.danilovfa.util.exception.respondError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.postController(service: PostService) {
    post("/posts") {
        try {
            val request = call.receive<PostCreateRequestTo>()
            val post = service.createPost(request.storyId, request.content).toResponse()
            call.respond(HttpStatusCode.Created, post)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    get("/posts/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            val response = service.getPostById(id).toResponse()
            call.respond(HttpStatusCode.OK, response)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    get("/posts") {
        try {
            val response = service.getPosts().map { it.toResponse() }
            call.respond(HttpStatusCode.OK, response)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    put("/posts") {
        try {
            val request = call.receive<PostUpdateRequestTo>()
            val response = service.updatePost(request.id, request.storyId, request.content).toResponse()
            call.respond(HttpStatusCode.OK, response)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    delete("/posts/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            service.deletePost(id)
            call.respond(HttpStatusCode.NoContent)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }
}