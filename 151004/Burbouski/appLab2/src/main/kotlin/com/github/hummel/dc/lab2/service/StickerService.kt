package com.github.hummel.dc.lab2.service

import com.github.hummel.dc.lab2.dto.request.StickerRequestTo
import com.github.hummel.dc.lab2.dto.request.StickerRequestToId
import com.github.hummel.dc.lab2.dto.response.StickerResponseTo

interface StickerService {
	fun getAll(): List<StickerResponseTo>

	fun create(messageRequestTo: StickerRequestTo?): StickerResponseTo?

	fun deleteById(id: Long): Boolean

	fun getById(id: Long): StickerResponseTo?

	fun update(messageRequestToId: StickerRequestToId?): StickerResponseTo?
}