package com.github.hummel.dc.lab2.module

import com.github.hummel.dc.lab2.bean.Author
import com.github.hummel.dc.lab2.bean.Issue
import com.github.hummel.dc.lab2.bean.Message
import com.github.hummel.dc.lab2.bean.Sticker
import com.github.hummel.dc.lab2.service.AuthorService
import com.github.hummel.dc.lab2.service.IssueService
import com.github.hummel.dc.lab2.service.MessageService
import com.github.hummel.dc.lab2.service.StickerService
import com.github.hummel.dc.lab2.service.impl.AuthorServiceImpl
import com.github.hummel.dc.lab2.service.impl.IssueServiceImpl
import com.github.hummel.dc.lab2.service.impl.MessageServiceImpl
import com.github.hummel.dc.lab2.service.impl.StickerServiceImpl
import com.github.hummel.dc.lab2.util.BaseRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
	single<AuthorService> {
		val repository: BaseRepository<Author, Long> = get(authorsRepositoryQualifier)

		AuthorServiceImpl(repository)
	}
	single<IssueService> {
		val repository: BaseRepository<Issue, Long> = get(issuesRepositoryQualifier)

		IssueServiceImpl(repository)
	}
	single<MessageService> {
		val repository: BaseRepository<Message, Long> = get(messagesRepositoryQualifier)

		MessageServiceImpl(repository)
	}
	single<StickerService> {
		val repository: BaseRepository<Sticker, Long> = get(stickersRepositoryQualifier)

		StickerServiceImpl(repository)
	}
}