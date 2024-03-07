package com.github.hummel.dc.lab2.repository.impl

import com.github.hummel.dc.lab2.bean.Author
import com.github.hummel.dc.lab2.dao.AuthorDao
import com.github.hummel.dc.lab2.dao.impl.AuthorDaoImpl
import com.github.hummel.dc.lab2.repository.AuthorsRepository

class AuthorsRepositoryImpl(
	private val authorDao: AuthorDao
) : AuthorsRepository {
	override suspend fun create(author: Author): Long? {
		return try {
			authorDao.create(author)
		} catch (e: Exception) {
			null
		}
	}

	override suspend fun getById(id: Long): Author? {
		return try {
			authorDao.getById(id)
		} catch (e: Exception) {
			null
		}
	}

	override suspend fun getAll(): List<Author?> = authorDao.getAll()

	override suspend fun update(author: Author): Boolean = authorDao.update(author) > 0

	override suspend fun deleteById(id: Long): Boolean = authorDao.deleteById(id) > 0
}