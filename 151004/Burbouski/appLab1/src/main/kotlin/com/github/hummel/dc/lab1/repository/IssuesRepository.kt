package com.github.hummel.dc.lab1.repository

import com.github.hummel.dc.lab1.bean.Issue

interface IssuesRepository {
	val data: MutableList<Pair<Long, Issue>>

	suspend fun getById(id: Long): Issue?

	suspend fun addItem(id: Long, item: Issue): Issue?

	suspend fun getLastItem(): Issue?

	suspend fun deleteById(id: Long): Boolean

	suspend fun getAll(): List<Issue>
}