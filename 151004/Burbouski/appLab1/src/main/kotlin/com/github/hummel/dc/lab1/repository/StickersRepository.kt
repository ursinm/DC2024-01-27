package com.github.hummel.dc.lab1.repository

import com.github.hummel.dc.lab1.bean.Sticker

interface StickersRepository {
	val data: MutableList<Pair<Long, Sticker>>

	suspend fun getItemById(id: Long): Pair<Long, Sticker>? = data.find { it.first == id }

	suspend fun addItem(id: Long, item: Sticker): Sticker?

	suspend fun getLastItem(): Sticker?

	suspend fun removeItem(id: Long): Boolean
}