package com.github.hummel.dc.lab1.repository

import com.github.hummel.dc.lab1.bean.Author

interface AuthorsRepository {
	val data: MutableList<Pair<Long, Author>>

	fun getItemById(id: Long): Pair<Long, Author>? = data.find { it.first == id }

	fun addItem(id: Long, item: Author): Author?

	fun getLastItem(): Author?

	fun removeItem(id: Long): Boolean
}