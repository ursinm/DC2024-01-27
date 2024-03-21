package by.bashlikovvv.di

import by.bashlikovvv.data.local.dao.EditorOfflineSource
import by.bashlikovvv.data.local.dao.PostOfflineSource
import by.bashlikovvv.data.local.dao.TagOfflineSource
import by.bashlikovvv.data.local.dao.TweetOfflineSource
import by.bashlikovvv.data.repository.EditorsRepository
import by.bashlikovvv.data.repository.PostsRepository
import by.bashlikovvv.data.repository.TagsRepository
import by.bashlikovvv.data.repository.TweetsRepository
import by.bashlikovvv.domain.repository.IEditorsRepository
import by.bashlikovvv.domain.repository.IPostsRepository
import by.bashlikovvv.domain.repository.ITagsRepository
import by.bashlikovvv.domain.repository.ITweetsRepository
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import java.sql.Connection

val editorsRepositoryQualifier = StringQualifier("editors_repository")
val tweetsRepositoryQualifier = StringQualifier("tweets_repository")
val postsRepositoryQualifier = StringQualifier("posts_repository")
val tagsRepositoryQualifier = StringQualifier("tags_repository")

val dataModule = module {

    single<EditorOfflineSource> {
        val dbConnection: Connection = get()

        EditorOfflineSource(dbConnection)
    }

    single<TweetOfflineSource> {
        val dbConnection: Connection = get()

        TweetOfflineSource(dbConnection)
    }

    single<PostOfflineSource> {
        val dbConnection: Connection = get()

        PostOfflineSource(dbConnection)
    }

    single<TagOfflineSource> {
        val dbConnection: Connection = get()

        TagOfflineSource(dbConnection)
    }

    single<IEditorsRepository>(editorsRepositoryQualifier) {
        val editorOfflineSource: EditorOfflineSource = get()

        EditorsRepository(editorOfflineSource)
    }

    single<ITweetsRepository>(tweetsRepositoryQualifier) {
        val tweetOfflineSource: TweetOfflineSource = get()

        TweetsRepository(tweetOfflineSource)
    }

    single<IPostsRepository>(postsRepositoryQualifier) {
        val postOfflineSource: PostOfflineSource = get()

        PostsRepository(postOfflineSource)
    }

    single<ITagsRepository>(tagsRepositoryQualifier) {
        val tagOfflineSource: TagOfflineSource = get()

        TagsRepository(tagOfflineSource)
    }

}