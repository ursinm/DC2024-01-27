package com.github.hummel.dc.lab1.dto.response

import kotlinx.serialization.Serializable

@Serializable
class StickerResponseTo(
	val id: Long, val name: String
)