package com.github.hummel.dc.lab1.repository.impl

import com.github.hummel.dc.lab1.bean.Issue
import com.github.hummel.dc.lab1.repository.IssuesRepository

class IssuesRepositoryImpl : IssuesRepository {
	override val data: MutableList<Pair<Long, Issue>> = mutableListOf()

	override suspend fun getById(id: Long): Issue? = data.find { it.first == id }?.second

	override suspend fun getLastItem(): Issue? {
		var maxKey = 0L

		data.forEach { maxKey = maxOf(it.first, maxKey) }

		return data.find { it.first == maxKey }?.second
	}

	override suspend fun addItem(id: Long, item: Issue): Issue? {
		val flag = data.add(id to item)
		return if (flag) item else null
	}

	override suspend fun deleteById(id: Long): Boolean = data.removeIf { it.first == id }

	override suspend fun getAll(): List<Issue> = data.map { it.second }
}