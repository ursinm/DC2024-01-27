package com.github.hummel.dc.lab2.repository

import com.github.hummel.dc.lab2.bean.Author

interface AuthorsRepository {
	suspend fun create(author: Author): Long?

	suspend fun getById(id: Long): Author?

	suspend fun getAll(): List<Author?>

	suspend fun update(author: Author): Boolean

	suspend fun deleteById(id: Long): Boolean
}