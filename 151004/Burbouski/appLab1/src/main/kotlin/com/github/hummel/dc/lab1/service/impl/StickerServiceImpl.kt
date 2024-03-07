package com.github.hummel.dc.lab1.service.impl

import com.github.hummel.dc.lab1.dto.request.StickerRequestTo
import com.github.hummel.dc.lab1.dto.request.StickerRequestToId
import com.github.hummel.dc.lab1.dto.response.StickerResponseTo
import com.github.hummel.dc.lab1.repository.StickersRepository
import com.github.hummel.dc.lab1.service.StickerService

class StickerServiceImpl(
	private val messageRepository: StickersRepository
) : StickerService {
	override suspend fun getAll(): List<StickerResponseTo> {
		val result = messageRepository.data.map { it.second }

		return result.map { it.toResponse() }
	}

	override suspend fun create(messageRequestTo: StickerRequestTo?): StickerResponseTo? {
		val id = if (messageRepository.data.isEmpty()) {
			-1
		} else {
			messageRepository.getLastItem()?.id ?: return null
		} + 1

		val bean = messageRequestTo?.toBean(id) ?: return null
		val result = messageRepository.addItem(bean.id, bean) ?: return null

		return result.toResponse()
	}

	override suspend fun deleteById(id: Long): Boolean = messageRepository.deleteById(id)

	override suspend fun getById(id: Long): StickerResponseTo? {
		val result = messageRepository.getById(id) ?: return null

		return result.toResponse()
	}

	override suspend fun update(messageRequestToId: StickerRequestToId?): StickerResponseTo? {
		val bean = messageRequestToId?.toBean() ?: return null
		val result = messageRepository.addItem(bean.id, bean) ?: return null

		return result.toResponse()
	}
}