package by.bashlikovvv.di

import by.bashlikovvv.api.dto.response.EditorDto
import by.bashlikovvv.api.dto.response.PostDto
import by.bashlikovvv.api.dto.response.TagDto
import by.bashlikovvv.api.dto.response.TweetDto
import by.bashlikovvv.domain.repository.IEditorsRepository
import by.bashlikovvv.domain.repository.IPostsRepository
import by.bashlikovvv.domain.repository.ITagsRepository
import by.bashlikovvv.domain.repository.ITweetsRepository
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val editorsRepositoryQualifier = StringQualifier("editors_repository")
val tweetsRepositoryQualifier = StringQualifier("tweets_repository")
val postsRepositoryQualifier = StringQualifier("posts_repository")
val tagsRepositoryQualifier = StringQualifier("tags_repository")

val dataModule = module {

    single<IEditorsRepository>(editorsRepositoryQualifier) {
        object : IEditorsRepository {
            override val data: MutableList<Pair<Long, EditorDto>> = mutableListOf()

            override fun getLastItem(): EditorDto? {
                var maxKey = 0L
                data.forEach { maxKey = maxOf(it.first, maxKey) }

                return data.find { it.first == maxKey }?.second
            }

            override fun addItem(id: Long, item: EditorDto): EditorDto? {
                val flag = data.add(id to item)

                return if (flag) {
                    item
                } else {
                    null
                }
            }

            override fun removeItem(id: Long): Boolean {
                return data.removeIf { it.first == id }
            }
        }
    }

    single<ITweetsRepository>(tweetsRepositoryQualifier) {
        object : ITweetsRepository {
            override val data: MutableList<Pair<Long, TweetDto>> = mutableListOf()

            override fun getLastItem(): TweetDto? {
                var maxKey = 0L
                data.forEach { maxKey = maxOf(it.first, maxKey) }

                return data.find { it.first == maxKey }?.second
            }

            override fun removeItem(id: Long): Boolean {
                return data.removeIf { it.first == id }
            }

            override fun addItem(id: Long, item: TweetDto): TweetDto? {
                val flag = data.add(id to item)

                return if (flag) {
                    item
                } else {
                    null
                }
            }
        }
    }

    single<IPostsRepository>(postsRepositoryQualifier) {
        object : IPostsRepository {
            override val data: MutableList<Pair<Long, PostDto>> = mutableListOf()

            override fun getLastItem(): PostDto? {
                var maxKey = 0L
                data.forEach { maxKey = maxOf(it.first, maxKey) }

                return data.find { it.first == maxKey }?.second
            }

            override fun removeItem(id: Long): Boolean {
                return data.removeIf { it.first == id }
            }

            override fun addItem(id: Long, item: PostDto): PostDto? {
                val flag = data.add(id to item)

                return if (flag) {
                    item
                } else {
                    null
                }
            }
        }
    }

    single<ITagsRepository>(tagsRepositoryQualifier) {
        object : ITagsRepository {
            override val data: MutableList<Pair<Long, TagDto>> = mutableListOf()

            override fun getLastItem(): TagDto? {
                var maxKey = 0L
                data.forEach { maxKey = maxOf(it.first, maxKey) }

                return data.find { it.first == maxKey }?.second
            }

            override fun removeItem(id: Long): Boolean {
                return data.removeIf { it.first == id }
            }

            override fun addItem(id: Long, item: TagDto): TagDto? {
                val flag = data.add(id to item)

                return if (flag) {
                    item
                } else {
                    null
                }
            }
        }
    }

}