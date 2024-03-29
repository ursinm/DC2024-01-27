package com.danilovfa.di

import com.danilovfa.database.tables.*
import org.koin.dsl.module

val daosModule = module {
    single<PostsDao> { PostsTable }
    single<StickersDao> { StickersTable }
    single<StoriesDao> { StoriesTable }
    single<UsersDao> { UsersTable }
}