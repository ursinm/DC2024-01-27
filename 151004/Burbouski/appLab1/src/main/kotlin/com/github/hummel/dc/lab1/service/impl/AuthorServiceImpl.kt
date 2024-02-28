package com.github.hummel.dc.lab1.service.impl

import com.github.hummel.dc.lab1.bean.Author
import com.github.hummel.dc.lab1.dto.request.AuthorRequestTo
import com.github.hummel.dc.lab1.dto.request.AuthorRequestToId
import com.github.hummel.dc.lab1.dto.response.AuthorResponseTo
import com.github.hummel.dc.lab1.service.AuthorService
import com.github.hummel.dc.lab1.util.BaseRepository

class AuthorServiceImpl(
	private val authorRepository: BaseRepository<Author, Long>
) : AuthorService {

	override fun getAll(): List<AuthorResponseTo> {
		val result = authorRepository.data.map { it.second }

		return result.map { it.toResponse() }
	}

	override fun create(authorRequestTo: AuthorRequestTo?): AuthorResponseTo? {
		val id = if (authorRepository.data.isEmpty()) {
			-1
		} else {
			authorRepository.getLastItem()?.id ?: return null
		} + 1

		val bean = authorRequestTo?.toBean(id) ?: return null
		val result = authorRepository.addItem(bean.id, bean)

		return result?.toResponse()
	}

	override fun deleteById(id: Long): Boolean = authorRepository.removeItem(id)

	override fun getById(id: Long): AuthorResponseTo? {
		val result = authorRepository.getItemById(id)?.second

		return result?.toResponse()
	}

	override fun update(authorRequestToId: AuthorRequestToId?): AuthorResponseTo? {
		val bean = authorRequestToId?.toBean() ?: return null
		val result = authorRepository.addItem(bean.id, bean)

		return result?.toResponse()
	}
}