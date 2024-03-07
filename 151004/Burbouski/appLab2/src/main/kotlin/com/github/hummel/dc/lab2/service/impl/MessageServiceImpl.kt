package com.github.hummel.dc.lab2.service.impl

import com.github.hummel.dc.lab2.bean.Message
import com.github.hummel.dc.lab2.dto.request.MessageRequestTo
import com.github.hummel.dc.lab2.dto.request.MessageRequestToId
import com.github.hummel.dc.lab2.dto.response.MessageResponseTo
import com.github.hummel.dc.lab2.service.MessageService
import com.github.hummel.dc.lab2.util.BaseRepository

class MessageServiceImpl(
	private val messageRepository: BaseRepository<Message, Long>
) : MessageService {
	override fun getAll(): List<MessageResponseTo> {
		val result = messageRepository.data.map { it.second }

		return result.map { it.toResponse() }
	}

	override fun create(messageRequestTo: MessageRequestTo?): MessageResponseTo? {
		val id = if (messageRepository.data.isEmpty()) {
			-1
		} else {
			messageRepository.getLastItem()?.id ?: return null
		} + 1

		val bean = messageRequestTo?.toBean(id) ?: return null
		val result = messageRepository.addItem(bean.id, bean)

		return result?.toResponse()
	}

	override fun deleteById(id: Long): Boolean = messageRepository.removeItem(id)

	override fun getById(id: Long): MessageResponseTo? {
		val result = messageRepository.getItemById(id)?.second

		return result?.toResponse()
	}

	override fun update(messageRequestToId: MessageRequestToId?): MessageResponseTo? {
		val bean = messageRequestToId?.toBean() ?: return null
		val result = messageRepository.addItem(bean.id, bean)

		return result?.toResponse()
	}
}