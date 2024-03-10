package by.bashlikovvv.di

import by.bashlikovvv.domain.repository.IEditorsRepository
import by.bashlikovvv.domain.repository.IPostsRepository
import by.bashlikovvv.domain.repository.ITagsRepository
import by.bashlikovvv.domain.repository.ITweetsRepository
import by.bashlikovvv.services.EditorService
import by.bashlikovvv.services.PostService
import by.bashlikovvv.services.TagService
import by.bashlikovvv.services.TweetService
import by.bashlikovvv.services.impl.EditorServiceImpl
import by.bashlikovvv.services.impl.PostServiceImpl
import by.bashlikovvv.services.impl.TagServiceImpl
import by.bashlikovvv.services.impl.TweetServiceImpl
import org.koin.dsl.module

val appModule = module {

    single<EditorService> {
        val repository: IEditorsRepository = get(editorsRepositoryQualifier)

        EditorServiceImpl(repository)
    }

    single<TweetService> {
        val repository: ITweetsRepository = get(tweetsRepositoryQualifier)

        TweetServiceImpl(repository)
    }

    single<PostService> {
        val repository: IPostsRepository = get(postsRepositoryQualifier)

        PostServiceImpl(repository)
    }

    single<TagService> {
        val repository: ITagsRepository = get(tagsRepositoryQualifier)

        TagServiceImpl(repository)
    }

}