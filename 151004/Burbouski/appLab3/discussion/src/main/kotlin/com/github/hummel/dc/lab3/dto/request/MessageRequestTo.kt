package com.github.hummel.dc.lab3.dto.request

import com.github.hummel.dc.lab3.bean.Message
import kotlinx.serialization.Serializable

@Serializable
data class MessageRequestTo(
	private val issueId: Long, private val country: String, private val content: String
) {
	fun toBean(id: Long?): Message = Message(
		id, country, issueId, content
	)

	init {
		require(content.length in 4..2048)
	}
}