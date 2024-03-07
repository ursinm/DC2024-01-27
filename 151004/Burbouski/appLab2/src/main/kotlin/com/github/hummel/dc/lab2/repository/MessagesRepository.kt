package com.github.hummel.dc.lab2.repository

import com.github.hummel.dc.lab2.bean.Message

interface MessagesRepository {
	val data: MutableList<Pair<Long, Message>>

	suspend fun getItemById(id: Long): Pair<Long, Message>? = data.find { it.first == id }

	suspend fun addItem(id: Long, item: Message): Message?

	suspend fun getLastItem(): Message?

	suspend fun removeItem(id: Long): Boolean
}