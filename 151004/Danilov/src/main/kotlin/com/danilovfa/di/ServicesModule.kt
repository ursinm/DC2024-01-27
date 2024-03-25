package com.danilovfa.di

import com.danilovfa.service.PostService
import com.danilovfa.service.StickerService
import com.danilovfa.service.StoryService
import com.danilovfa.service.UserService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val servicesModule = module {
    single { PostService(dao = get()) }
    single { StickerService(dao = get()) }
    single { StoryService(dao = get()) }
    single { UserService(dao = get()) }
}