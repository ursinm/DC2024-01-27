package com.github.hummel.dc.lab1.service

import com.github.hummel.dc.lab1.dto.request.MessageRequestTo
import com.github.hummel.dc.lab1.dto.request.MessageRequestToId
import com.github.hummel.dc.lab1.dto.response.MessageResponseTo

interface MessageService {
	suspend fun getAll(): List<MessageResponseTo>

	suspend fun create(messageRequestTo: MessageRequestTo?): MessageResponseTo?

	suspend fun deleteById(id: Long): Boolean

	suspend fun getById(id: Long): MessageResponseTo?

	suspend fun update(messageRequestToId: MessageRequestToId?): MessageResponseTo?
}