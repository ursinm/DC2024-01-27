package com.github.hummel.dc.lab1.service

import com.github.hummel.dc.lab1.dto.request.StickerRequestTo
import com.github.hummel.dc.lab1.dto.request.StickerRequestToId
import com.github.hummel.dc.lab1.dto.response.StickerResponseTo

interface StickerService {
	suspend fun getAll(): List<StickerResponseTo>

	suspend fun create(messageRequestTo: StickerRequestTo?): StickerResponseTo?

	suspend fun deleteById(id: Long): Boolean

	suspend fun getById(id: Long): StickerResponseTo?

	suspend fun update(messageRequestToId: StickerRequestToId?): StickerResponseTo?
}