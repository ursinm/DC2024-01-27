package com.github.hummel.dc.lab1.repository

import com.github.hummel.dc.lab1.bean.Sticker

interface StickersRepository {
	val data: MutableList<Pair<Long, Sticker>>

	fun getItemById(id: Long): Pair<Long, Sticker>? = data.find { it.first == id }

	fun addItem(id: Long, item: Sticker): Sticker?

	fun getLastItem(): Sticker?

	fun removeItem(id: Long): Boolean
}