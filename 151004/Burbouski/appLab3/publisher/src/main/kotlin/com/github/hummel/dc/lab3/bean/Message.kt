package com.github.hummel.dc.lab3.bean

import kotlinx.serialization.Serializable

@Serializable
data class Message(
	val id: Long?, val country: String?, val issueId: Long, val content: String
)