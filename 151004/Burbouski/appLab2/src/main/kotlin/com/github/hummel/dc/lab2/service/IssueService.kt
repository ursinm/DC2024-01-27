package com.github.hummel.dc.lab2.service

import com.github.hummel.dc.lab2.dto.request.IssueRequestTo
import com.github.hummel.dc.lab2.dto.request.IssueRequestToId
import com.github.hummel.dc.lab2.dto.response.IssueResponseTo

interface IssueService {
	fun getAll(): List<IssueResponseTo>

	fun create(issueRequestTo: IssueRequestTo?): IssueResponseTo?

	fun deleteById(id: Long): Boolean

	fun getById(id: Long): IssueResponseTo?

	fun update(issueRequestToId: IssueRequestToId?): IssueResponseTo?
}