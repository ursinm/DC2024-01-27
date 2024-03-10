package by.bashlikovvv.services

import by.bashlikovvv.api.dto.request.CreatePostDto
import by.bashlikovvv.api.dto.request.UpdatePostDto
import by.bashlikovvv.api.dto.response.PostDto

interface PostService {

    suspend fun create(createPostDto: CreatePostDto): PostDto?

    suspend fun update(postId: Long, updatePostDto: UpdatePostDto): PostDto?

    suspend fun getById(postId: Long): PostDto?

    suspend fun getAll(): List<PostDto?>

    suspend fun delete(postId: Long): Boolean

}