package com.github.hummel.dc.lab2.module

import com.github.hummel.dc.lab2.bean.Author
import com.github.hummel.dc.lab2.bean.Issue
import com.github.hummel.dc.lab2.bean.Message
import com.github.hummel.dc.lab2.bean.Sticker
import com.github.hummel.dc.lab2.util.BaseRepository
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val authorsRepositoryQualifier: StringQualifier = StringQualifier("authors_repository")
val issuesRepositoryQualifier: StringQualifier = StringQualifier("issues_repository")
val messagesRepositoryQualifier: StringQualifier = StringQualifier("messages_repository")
val stickersRepositoryQualifier: StringQualifier = StringQualifier("stickers_repository")

val dataModule: Module = module {
	single<BaseRepository<Author, Long>>(authorsRepositoryQualifier) {
		object : BaseRepository<Author, Long> {
			override val data: MutableList<Pair<Long, Author>> = mutableListOf()

			override fun getLastItem(): Author? {
				var maxKey = 0L
				data.forEach { maxKey = maxOf(it.first, maxKey) }

				return data.find { it.first == maxKey }?.second
			}

			override fun addItem(id: Long, item: Author): Author? {
				val flag = data.add(id to item)

				return if (flag) item else null
			}

			override fun removeItem(id: Long): Boolean = data.removeIf { it.first == id }
		}
	}
	single<BaseRepository<Issue, Long>>(issuesRepositoryQualifier) {
		object : BaseRepository<Issue, Long> {
			override val data: MutableList<Pair<Long, Issue>> = mutableListOf()

			override fun getLastItem(): Issue? {
				var maxKey = 0L
				data.forEach { maxKey = maxOf(it.first, maxKey) }

				return data.find { it.first == maxKey }?.second
			}

			override fun addItem(id: Long, item: Issue): Issue? {
				val flag = data.add(id to item)

				return if (flag) item else null
			}

			override fun removeItem(id: Long): Boolean = data.removeIf { it.first == id }
		}
	}
	single<BaseRepository<Message, Long>>(messagesRepositoryQualifier) {
		object : BaseRepository<Message, Long> {
			override val data: MutableList<Pair<Long, Message>> = mutableListOf()

			override fun getLastItem(): Message? {
				var maxKey = 0L
				data.forEach { maxKey = maxOf(it.first, maxKey) }

				return data.find { it.first == maxKey }?.second
			}

			override fun addItem(id: Long, item: Message): Message? {
				val flag = data.add(id to item)

				return if (flag) item else null
			}

			override fun removeItem(id: Long): Boolean = data.removeIf { it.first == id }
		}
	}
	single<BaseRepository<Sticker, Long>>(stickersRepositoryQualifier) {
		object : BaseRepository<Sticker, Long> {
			override val data: MutableList<Pair<Long, Sticker>> = mutableListOf()

			override fun getLastItem(): Sticker? {
				var maxKey = 0L
				data.forEach { maxKey = maxOf(it.first, maxKey) }

				return data.find { it.first == maxKey }?.second
			}

			override fun addItem(id: Long, item: Sticker): Sticker? {
				val flag = data.add(id to item)

				return if (flag) item else null
			}

			override fun removeItem(id: Long): Boolean = data.removeIf { it.first == id }
		}
	}
}