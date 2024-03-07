package com.github.hummel.dc.lab2.repository.impl

import com.github.hummel.dc.lab2.bean.Sticker
import com.github.hummel.dc.lab2.repository.StickersRepository

class StickersRepositoryImpl : StickersRepository {
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