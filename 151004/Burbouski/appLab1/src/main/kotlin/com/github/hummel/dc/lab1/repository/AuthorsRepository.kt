package com.github.hummel.dc.lab1.repository

import com.github.hummel.dc.lab1.bean.Author

interface AuthorsRepository {
	val data: MutableList<Pair<Long, Author>>

	suspend fun getById(id: Long): Author?

	suspend fun addItem(id: Long, item: Author): Author?

	suspend fun getLastItem(): Author?

	suspend fun deleteById(id: Long): Boolean

	suspend fun getAll(): List<Author>
}