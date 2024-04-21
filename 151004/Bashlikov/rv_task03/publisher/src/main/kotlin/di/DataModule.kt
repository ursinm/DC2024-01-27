package di

import by.bashlikovvv.domain.repository.ITagsRepository
import data.local.dao.EditorOfflineSource
import data.local.dao.TagOfflineSource
import data.local.dao.TweetOfflineSource
import data.repository.EditorsRepository
import data.repository.TagsRepository
import data.repository.TweetsRepository
import domain.repository.IEditorsRepository
import domain.repository.ITweetsRepository
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import java.sql.Connection

internal val editorsRepositoryQualifier = StringQualifier("editors_repository")
internal val tweetsRepositoryQualifier = StringQualifier("tweets_repository")
internal val tagsRepositoryQualifier = StringQualifier("tags_repository")

internal val dataModule = module {

    single<TweetOfflineSource> {
        val dbConnection: Connection = get()

        TweetOfflineSource(dbConnection)
    }

    single<TagOfflineSource> {
        val dbConnection: Connection = get()

        TagOfflineSource(dbConnection)
    }

    single<EditorOfflineSource> {
        val dbConnection: Connection = get()

        EditorOfflineSource(dbConnection)
    }

    single<IEditorsRepository>(editorsRepositoryQualifier) {
        val editorOfflineSource: EditorOfflineSource = get()

        EditorsRepository(editorOfflineSource)
    }

    single<ITweetsRepository>(tweetsRepositoryQualifier) {
        val tweetOfflineSource: TweetOfflineSource = get()

        TweetsRepository(tweetOfflineSource)
    }

    single<ITagsRepository>(tagsRepositoryQualifier) {
        val tagOfflineSource: TagOfflineSource = get()

        TagsRepository(tagOfflineSource)
    }

}