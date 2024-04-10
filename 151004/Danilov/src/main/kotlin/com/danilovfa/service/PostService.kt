package com.danilovfa.service

import com.danilovfa.database.tables.PostsDao
import com.danilovfa.model.post.Post
import com.danilovfa.util.exception.exceptions.ForbiddenException
import io.ktor.server.plugins.*
import kotlin.jvm.Throws

class PostService(
    private val dao: PostsDao
) {

    @Throws(IllegalArgumentException::class, NotFoundException::class, ForbiddenException::class)
    suspend fun createPost(storyId: Long, content: String): Post {
        if (content.length !in 2..2048) throw IllegalArgumentException("Wrong content size (not 2..2048)")
        if (dao.canCreate(storyId).not()) throw ForbiddenException("Story does not exist")
        val id = dao.insertPost(storyId, content)
        return getPostById(id)
    }

    suspend fun getPosts(): List<Post> {
        return dao.getPosts()
    }

    @Throws(NotFoundException::class, IllegalArgumentException::class)
    suspend fun getPostById(id: Long?): Post {
        if (id == null) throw IllegalArgumentException("Wrong post id")
        return dao.getPost(id) ?: throw NotFoundException("Post not found")
    }

    @Throws(NotFoundException::class, IllegalArgumentException::class)
    suspend fun updatePost(id: Long, storyId: Long, content: String): Post {
        if (content.length !in 2..2048) throw IllegalArgumentException("Wrong content size (not 2..2048)")

        return dao.updatePost(id, storyId, content) ?: throw NotFoundException("Post not found")
    }

    @Throws(NotFoundException::class, IllegalArgumentException::class)
    suspend fun deletePost(id: Long?) {
        if (id == null) throw IllegalArgumentException("Wrong post id")
        if (dao.deletePost(id).not()) throw NotFoundException("Post not found")
    }
}