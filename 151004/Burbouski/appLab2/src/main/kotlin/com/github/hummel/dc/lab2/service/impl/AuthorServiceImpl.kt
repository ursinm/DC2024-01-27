package com.github.hummel.dc.lab2.service.impl

import com.github.hummel.dc.lab2.dto.request.AuthorRequestTo
import com.github.hummel.dc.lab2.dto.request.AuthorRequestToId
import com.github.hummel.dc.lab2.dto.response.AuthorResponseTo
import com.github.hummel.dc.lab2.repository.AuthorsRepository
import com.github.hummel.dc.lab2.service.AuthorService

class AuthorServiceImpl(
	private val authorRepository: AuthorsRepository
) : AuthorService {
	override suspend fun create(authorRequestTo: AuthorRequestTo?): AuthorResponseTo? {
		val bean = authorRequestTo?.toBean(null) ?: return null
		val id = authorRepository.create(bean) ?: return null

		return bean.copy(id = id).toResponse()
	}

	override suspend fun update(authorRequestToId: AuthorRequestToId?): AuthorResponseTo? {
		val bean = authorRequestToId?.toBean() ?: return null

		if (!authorRepository.update(bean)) {
			throw Exception("Exception during editor updating!")
		}

		return bean.toResponse()
	}

	override suspend fun getById(id: Long): AuthorResponseTo? {
		val editorEntity = authorRepository.getById(id) ?: return null

		return editorEntity.toResponse()
	}

	override suspend fun deleteById(id: Long): Boolean = authorRepository.deleteById(id)

	override suspend fun getAll(): List<AuthorResponseTo> =
		authorRepository.getAll().filterNotNull().map { it.toResponse() }
}