package com.github.hummel.dc.lab1.repository.impl

import com.github.hummel.dc.lab1.bean.Author
import com.github.hummel.dc.lab1.repository.AuthorsRepository

class AuthorsRepositoryImpl : AuthorsRepository {
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