package api.controller.routing

import api.dto.request.CreatePostDto
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

internal fun Route.postsRouting() {
    val client = getHttpClient()

    getPosts(client)
    createPost(client)
    deletePostById(client)
    getPostById(client)
    updatePost(client)
}

private fun getHttpClient() = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }
}

private fun Route.getPosts(client: HttpClient) {
    get("/posts") {
        call.respond(client.get("http://0.0.0.0:24130/api/v1.0/posts").bodyAsText())
    }
}

private fun Route.createPost(client: HttpClient) {
    post("/posts") {
        val body = call.receive<CreatePostDto>()
        val result = client.post("http://localhost:24130/api/v1.0/posts") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        call.respond(
            status = result.status,
            message = result.bodyAsText()
        )
    }
}

private fun Route.deletePostById(client: HttpClient) {
    delete("/posts/{id?}") {
        val id = call.parameters["id"]
        val result = client.delete("http://localhost:24130/api/v1.0/posts/$id")
        call.respond(
            status = result.status,
            message = result.bodyAsText()
        )
    }
}

private fun Route.getPostById(client: HttpClient) {
    get("/posts/{id?}") {
        val id = call.parameters["id"]
        val result = client.get("http://localhost:24130/api/v1.0/posts/$id")
        call.respond(
            status = result.status,
            message = result.bodyAsText()
        )
    }
}

private fun Route.updatePost(client: HttpClient) {
    put("/posts") {
        val body = call.receive<CreatePostDto>()
        val result = client.put("http://localhost:24130/api/v1.0/posts") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        call.respond(
            status = result.status,
            message = result.bodyAsText()
        )
    }
}