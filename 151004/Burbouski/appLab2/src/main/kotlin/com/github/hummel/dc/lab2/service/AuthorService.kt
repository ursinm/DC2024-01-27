package com.github.hummel.dc.lab2.service

import com.github.hummel.dc.lab2.dto.request.AuthorRequestTo
import com.github.hummel.dc.lab2.dto.request.AuthorRequestToId
import com.github.hummel.dc.lab2.dto.response.AuthorResponseTo

interface AuthorService {
	fun getAll(): List<AuthorResponseTo>

	fun create(authorRequestTo: AuthorRequestTo?): AuthorResponseTo?

	fun deleteById(id: Long): Boolean

	fun getById(id: Long): AuthorResponseTo?

	fun update(authorRequestToId: AuthorRequestToId?): AuthorResponseTo?
}