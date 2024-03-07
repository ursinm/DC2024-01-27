package com.github.hummel.dc.lab2.repository

import com.github.hummel.dc.lab2.bean.Issue

interface IssuesRepository {
	val data: MutableList<Pair<Long, Issue>>

	suspend fun getItemById(id: Long): Pair<Long, Issue>? = data.find { it.first == id }

	suspend fun addItem(id: Long, item: Issue): Issue?

	suspend fun getLastItem(): Issue?

	suspend fun removeItem(id: Long): Boolean
}