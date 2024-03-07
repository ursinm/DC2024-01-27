package by.bashlikovvv.services

import by.bashlikovvv.api.dto.request.CreatePostDto
import by.bashlikovvv.api.dto.request.UpdatePostDto
import by.bashlikovvv.api.dto.response.PostDto

interface PostService {

    fun create(createPostDto: CreatePostDto): PostDto?

    fun update(postId: Long, updatePostDto: UpdatePostDto): PostDto?

    fun getById(postId: Long): PostDto?

    fun getAll(): List<PostDto?>

    fun delete(postId: Long): Boolean

}