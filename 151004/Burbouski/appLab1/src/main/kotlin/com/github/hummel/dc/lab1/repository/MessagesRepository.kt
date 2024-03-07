package com.github.hummel.dc.lab1.repository

import com.github.hummel.dc.lab1.bean.Message

interface MessagesRepository {
	val data: MutableList<Pair<Long, Message>>

	suspend fun getById(id: Long): Message?

	suspend fun addItem(id: Long, item: Message): Message?

	suspend fun getLastItem(): Message?

	suspend fun deleteById(id: Long): Boolean

	suspend fun getAll(): List<Message>
}