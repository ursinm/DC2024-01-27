package com.github.hummel.dc.lab1.repository

import com.github.hummel.dc.lab1.bean.Author

interface AuthorsRepository {
	val data: MutableList<Pair<Long, Author>>

	suspend fun getItemById(id: Long): Pair<Long, Author>? = data.find { it.first == id }

	suspend fun addItem(id: Long, item: Author): Author?

	suspend fun getLastItem(): Author?

	suspend fun removeItem(id: Long): Boolean
}