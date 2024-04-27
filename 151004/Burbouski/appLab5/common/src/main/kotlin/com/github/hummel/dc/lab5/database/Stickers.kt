package com.github.hummel.dc.lab5.database

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
		val CREATE_TABLE_STICKERS: String = """
			CREATE TABLE $TABLE_NAME (
				$COLUMN_ID SERIAL PRIMARY KEY,
				$COLUMN_NAME VARCHAR(32)
			);
			""".trimIndent()

		val INSERT_STICKER: String = """
			INSERT INTO $TABLE_NAME (
				$COLUMN_NAME
			) VALUES (?);
			""".trimIndent()

		val SELECT_STICKER_BY_ID: String = """
			SELECT
				$COLUMN_NAME
			FROM $TABLE_NAME
			WHERE $COLUMN_ID = ?;
			""".trimIndent()

		val SELECT_STICKERS: String = """
			SELECT
				$COLUMN_ID,
				$COLUMN_NAME
			FROM $TABLE_NAME
			""".trimIndent()

		val UPDATE_STICKER: String = """
			UPDATE $TABLE_NAME
			SET $COLUMN_NAME = ?
			WHERE $COLUMN_ID = ?;
			""".trimIndent()

		val DELETE_STICKER: String = """
			DELETE FROM $TABLE_NAME
			WHERE $COLUMN_ID = ?;
			""".trimIndent()
	}
}