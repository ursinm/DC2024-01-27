package com.github.hummel.dc.lab2.database

enum class Stickers(private val col: String) {
	TABLE_NAME(
		"stickers"
	),
	COLUMN_ID(
		"stickers_id"
	),
	COLUMN_NAME(
		"stickers_name"
	);

	override fun toString(): String = col

	companion object {
	}
}