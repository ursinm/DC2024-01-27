package by.bashlikovvv.services.impl

import by.bashlikovvv.api.dto.mappers.CreatePostDtoToPostDtoMapper
import by.bashlikovvv.api.dto.mappers.UpdatePostDtoToPostDtoMapper
import by.bashlikovvv.api.dto.request.CreatePostDto
import by.bashlikovvv.api.dto.request.UpdatePostDto
import by.bashlikovvv.api.dto.response.PostDto
import by.bashlikovvv.domain.repository.IPostsRepository
import by.bashlikovvv.services.PostService

class PostServiceImpl(
    private val postRepository: IPostsRepository
) : PostService {
    override fun create(createPostDto: CreatePostDto): PostDto? {
        val lastItemId = if (postRepository.data.isEmpty()) {
            -1
        } else {
            postRepository.getLastItem()?.id ?: return null
        }
        val savedEntity = postRepository.addItem(
            id = lastItemId + 1,
            item = CreatePostDtoToPostDtoMapper(lastItemId + 1).mapFromEntity(createPostDto)
        )

        return savedEntity
    }

    override fun update(postId: Long, updatePostDto: UpdatePostDto): PostDto? {
        val savedEntity = postRepository.addItem(
            id = postId,
            item = UpdatePostDtoToPostDtoMapper().mapFromEntity(updatePostDto)
        )

        return savedEntity
    }

    override fun getById(postId: Long): PostDto? {
        return postRepository.getItemById(postId)?.second
    }

    override fun getAll(): List<PostDto?> {
        return postRepository.data.map { it.second }
    }

    override fun delete(postId: Long): Boolean {
        return postRepository.removeItem(postId)
    }
}