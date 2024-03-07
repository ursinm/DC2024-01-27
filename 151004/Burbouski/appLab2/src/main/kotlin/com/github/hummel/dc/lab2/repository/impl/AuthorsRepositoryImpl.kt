package com.github.hummel.dc.lab2.repository.impl

import com.github.hummel.dc.lab2.bean.Author
import com.github.hummel.dc.lab2.database.AuthorDao
import com.github.hummel.dc.lab2.repository.AuthorsRepository

class AuthorsRepositoryImpl(
	private val authorDao: AuthorDao
) : AuthorsRepository {
	override suspend fun create(author: Author): Long {
		return authorDao.create(author)
	}

	override suspend fun read(id: Long): Author? {
		return try {
			authorDao.read(id)
		} catch (e: Exception) {
			null
		}
	}

	override suspend fun readAll(): List<Author?> {
		return authorDao.readAll()
	}

	override suspend fun update(author: Author): Boolean {
		return authorDao.update(author) > 0
	}

	override suspend fun delete(id: Long): Boolean {
		return authorDao.delete(id) > 0
	}
}