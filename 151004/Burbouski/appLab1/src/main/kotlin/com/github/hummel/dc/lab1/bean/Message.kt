package com.github.hummel.dc.lab1.bean

import com.github.hummel.dc.lab1.dto.response.MessageResponseTo
import kotlinx.serialization.Serializable

@Suppress("MemberVisibilityCanBePrivate")
@Serializable
class Message(
	val id: Long, val issueId: Long, val content: String
) {
	fun toResponse(): MessageResponseTo = MessageResponseTo(id, issueId, content)
}