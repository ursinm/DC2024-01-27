package com.github.hummel.dc.lab2.service.impl

import com.github.hummel.dc.lab2.dto.request.IssueRequestTo
import com.github.hummel.dc.lab2.dto.request.IssueRequestToId
import com.github.hummel.dc.lab2.dto.response.IssueResponseTo
import com.github.hummel.dc.lab2.repository.IssuesRepository
import com.github.hummel.dc.lab2.service.IssueService
import java.sql.Timestamp

class IssueServiceImpl(
	private val issueRepository: IssuesRepository
) : IssueService {
	override suspend fun getAll(): List<IssueResponseTo> {
		val result = issueRepository.data.map { it.second }

		return result.map { it.toResponse() }
	}

	override suspend fun create(issueRequestTo: IssueRequestTo?): IssueResponseTo? {
		val id = if (issueRepository.data.isEmpty()) {
			-1
		} else {
			issueRepository.getLastItem()?.id ?: return null
		} + 1

		val created = Timestamp(System.currentTimeMillis())
		val modified = Timestamp(System.currentTimeMillis())

		val bean = issueRequestTo?.toBean(id, created, modified) ?: return null
		val result = issueRepository.addItem(bean.id, bean)

		return result?.toResponse()
	}

	override suspend fun deleteById(id: Long): Boolean = issueRepository.removeItem(id)

	override suspend fun getById(id: Long): IssueResponseTo? {
		val result = issueRepository.getItemById(id)?.second

		return result?.toResponse()
	}

	override suspend fun update(issueRequestToId: IssueRequestToId?): IssueResponseTo? {
		val created = Timestamp(System.currentTimeMillis())
		val modified = Timestamp(System.currentTimeMillis())

		val bean = issueRequestToId?.toBean(created, modified) ?: return null
		val result = issueRepository.addItem(bean.id, bean)

		return result?.toResponse()
	}
}