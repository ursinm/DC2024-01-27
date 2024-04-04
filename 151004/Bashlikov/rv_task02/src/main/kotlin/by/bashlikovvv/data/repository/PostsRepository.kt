package by.bashlikovvv.data.repository

import by.bashlikovvv.data.local.dao.PostOfflineSource
import by.bashlikovvv.data.local.model.PostEntity
import by.bashlikovvv.domain.repository.IPostsRepository

class PostsRepository(
    private val postOfflineSource: PostOfflineSource
) : IPostsRepository {
    override suspend fun create(postEntity: PostEntity): Long {
        return postOfflineSource.create(postEntity)
    }

    override suspend fun read(id: Long): PostEntity? {
        return try {
            postOfflineSource.read(id)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun readAll(): List<PostEntity?> {
        return postOfflineSource.readAll()
    }

    override suspend fun update(id: Long, postEntity: PostEntity): Boolean {
        return postOfflineSource.update(id, postEntity) > 0
    }

    override suspend fun delete(id: Long): Boolean {
        return postOfflineSource.delete(id) > 0
    }
}