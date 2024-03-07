package com.github.hummel.dc.lab1.repository

import com.github.hummel.dc.lab1.bean.Message

interface MessagesRepository {
	val data: MutableList<Pair<Long, Message>>

	fun getItemById(id: Long): Pair<Long, Message>? = data.find { it.first == id }

	fun addItem(id: Long, item: Message): Message?

	fun getLastItem(): Message?

	fun removeItem(id: Long): Boolean
}