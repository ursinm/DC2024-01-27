package com.github.hummel.dc.lab2.repository.impl

import com.github.hummel.dc.lab2.bean.Issue
import com.github.hummel.dc.lab2.repository.IssuesRepository

class IssuesRepositoryImpl : IssuesRepository {
	override val data: MutableList<Pair<Long, Issue>> = mutableListOf()

	override fun getLastItem(): Issue? {
		var maxKey = 0L

		data.forEach { maxKey = maxOf(it.first, maxKey) }

		return data.find { it.first == maxKey }?.second
	}

	override fun addItem(id: Long, item: Issue): Issue? {
		val flag = data.add(id to item)
		return if (flag) item else null
	}

	override fun removeItem(id: Long): Boolean = data.removeIf { it.first == id }
}