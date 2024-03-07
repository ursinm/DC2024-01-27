package com.github.hummel.dc.lab2.dao

import com.github.hummel.dc.lab2.bean.Author

interface AuthorDao {
	suspend fun create(authorEntity: Author): Long

	suspend fun getById(id: Long): Author

	suspend fun getAll(): List<Author?>

	suspend fun update(authorEntity: Author): Int

	suspend fun deleteById(id: Long): Int
}