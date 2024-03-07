package com.github.hummel.dc.lab2.util

interface BaseRepository<T, Index : Comparable<Index>> {
	val data: MutableList<Pair<Index, T>>

	fun getItemById(id: Index): Pair<Index, T>? = data.find { it.first == id }

	fun addItem(id: Index, item: T): T?

	fun getLastItem(): T?

	fun removeItem(id: Index): Boolean
}