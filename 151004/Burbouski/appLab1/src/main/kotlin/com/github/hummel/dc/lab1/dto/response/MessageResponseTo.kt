package com.github.hummel.dc.lab1.dto.response

import kotlinx.serialization.Serializable

@Serializable
class MessageResponseTo(
	val id: Long,
	val issueId: Long,
	val content: String
)