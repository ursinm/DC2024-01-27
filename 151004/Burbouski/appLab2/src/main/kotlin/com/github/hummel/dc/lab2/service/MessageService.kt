package com.github.hummel.dc.lab2.service

import com.github.hummel.dc.lab2.dto.request.MessageRequestTo
import com.github.hummel.dc.lab2.dto.request.MessageRequestToId
import com.github.hummel.dc.lab2.dto.response.MessageResponseTo

interface MessageService {
	fun getAll(): List<MessageResponseTo>

	fun create(messageRequestTo: MessageRequestTo?): MessageResponseTo?

	fun deleteById(id: Long): Boolean

	fun getById(id: Long): MessageResponseTo?

	fun update(messageRequestToId: MessageRequestToId?): MessageResponseTo?
}