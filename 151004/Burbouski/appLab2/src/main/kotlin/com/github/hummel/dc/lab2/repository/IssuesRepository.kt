package com.github.hummel.dc.lab2.repository

import com.github.hummel.dc.lab2.bean.Issue

interface IssuesRepository {
	val data: MutableList<Pair<Long, Issue>>

	fun getItemById(id: Long): Pair<Long, Issue>? = data.find { it.first == id }

	fun addItem(id: Long, item: Issue): Issue?

	fun getLastItem(): Issue?

	fun removeItem(id: Long): Boolean
}