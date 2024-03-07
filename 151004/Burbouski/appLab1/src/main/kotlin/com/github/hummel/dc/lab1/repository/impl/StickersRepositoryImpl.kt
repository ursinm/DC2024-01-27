package com.github.hummel.dc.lab1.repository.impl

import com.github.hummel.dc.lab1.bean.Sticker
import com.github.hummel.dc.lab1.repository.StickersRepository

class StickersRepositoryImpl : StickersRepository {
	override val data: MutableList<Pair<Long, Sticker>> = mutableListOf()

	override suspend fun getById(id: Long): Sticker? = data.find { it.first == id }?.second

	override suspend fun getLastItem(): Sticker? {
		var maxKey = 0L

		data.forEach { maxKey = maxOf(it.first, maxKey) }

		return data.find { it.first == maxKey }?.second
	}

	override suspend fun addItem(id: Long, item: Sticker): Sticker? {
		val flag = data.add(id to item)
		return if (flag) item else null
	}

	override suspend fun deleteById(id: Long): Boolean = data.removeIf { it.first == id }

	override suspend fun getAll(): List<Sticker> = data.map { it.second }
}