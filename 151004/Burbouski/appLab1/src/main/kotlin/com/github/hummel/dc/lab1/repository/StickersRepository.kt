package com.github.hummel.dc.lab1.repository

import com.github.hummel.dc.lab1.bean.Sticker

interface StickersRepository {
	val data: MutableList<Pair<Long, Sticker>>

	suspend fun getById(id: Long): Sticker?

	suspend fun addItem(id: Long, item: Sticker): Sticker?

	suspend fun getLastItem(): Sticker?

	suspend fun deleteById(id: Long): Boolean

	suspend fun getAll(): List<Sticker>
}