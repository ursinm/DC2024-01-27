package com.github.hummel.dc.lab2.repository.impl

import com.github.hummel.dc.lab2.bean.Message
import com.github.hummel.dc.lab2.repository.MessagesRepository

class MessagesRepositoryImpl : MessagesRepository {
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