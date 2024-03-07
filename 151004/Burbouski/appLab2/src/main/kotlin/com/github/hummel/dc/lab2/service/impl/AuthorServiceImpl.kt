package com.github.hummel.dc.lab2.service.impl

import com.github.hummel.dc.lab2.dto.request.AuthorRequestTo
import com.github.hummel.dc.lab2.dto.request.AuthorRequestToId
import com.github.hummel.dc.lab2.dto.response.AuthorResponseTo
import com.github.hummel.dc.lab2.repository.AuthorsRepository
import com.github.hummel.dc.lab2.service.AuthorService

class AuthorServiceImpl(
	private val authorRepository: AuthorsRepository
) : AuthorService {
	override suspend fun getAll(): List<AuthorResponseTo> {
		val result = authorRepository.data.map { it.second }

		return result.map { it.toResponse() }
	}

	override suspend fun create(authorRequestTo: AuthorRequestTo?): AuthorResponseTo? {
		val id = if (authorRepository.data.isEmpty()) {
			-1
		} else {
			authorRepository.getLastItem()?.id ?: return null
		} + 1

		val bean = authorRequestTo?.toBean(id) ?: return null
		val result = authorRepository.addItem(bean.id, bean)

		return result?.toResponse()
	}

	override suspend fun deleteById(id: Long): Boolean = authorRepository.removeItem(id)

	override suspend fun getById(id: Long): AuthorResponseTo? {
		val result = authorRepository.getItemById(id)?.second

		return result?.toResponse()
	}

	override suspend fun update(authorRequestToId: AuthorRequestToId?): AuthorResponseTo? {
		val bean = authorRequestToId?.toBean() ?: return null
		val result = authorRepository.addItem(bean.id, bean)

		return result?.toResponse()
	}
}