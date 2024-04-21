package com.github.hummel.dc.lab5.database

enum class Authors(private val col: String) {
	TABLE_NAME(
		"authors"
	),
	COLUMN_ID(
		"authors_id"
	),
	COLUMN_LOGIN(
		"authors_login"
	),
	COLUMN_PASSWORD(
		"authors_password"
	),
	COLUMN_FIRSTNAME(
		"authors_firstname"
	),
	COLUMN_LASTNAME(
		"authors_lastname"
	);

	override fun toString(): String = col

	companion object {
		val CREATE_TABLE_AUTHORS: String = """
			CREATE TABLE $TABLE_NAME (
				$COLUMN_ID SERIAL PRIMARY KEY, 
				$COLUMN_LOGIN VARCHAR(64) UNIQUE, 
				$COLUMN_PASSWORD VARCHAR(128), 
				$COLUMN_FIRSTNAME VARCHAR(64), 
				$COLUMN_LASTNAME VARCHAR(64)
			);
			""".trimIndent()

		val INSERT_AUTHOR: String = """
			INSERT
			INTO $TABLE_NAME (
				$COLUMN_LOGIN, 
				$COLUMN_PASSWORD, 
				$COLUMN_FIRSTNAME, 
				$COLUMN_LASTNAME
			) VALUES (?, ?, ?, ?);
			""".trimIndent()

		val SELECT_AUTHOR_BY_ID: String = """
			SELECT
				$COLUMN_LOGIN, 
				$COLUMN_PASSWORD, 
				$COLUMN_FIRSTNAME, 
				$COLUMN_LASTNAME 
			FROM $TABLE_NAME 
			WHERE $COLUMN_ID = ?;
			""".trimIndent()

		val SELECT_AUTHORS: String = """
			SELECT 
				$COLUMN_ID, 
				$COLUMN_LOGIN, 
				$COLUMN_PASSWORD, 
				$COLUMN_FIRSTNAME, 
				$COLUMN_LASTNAME
			FROM $TABLE_NAME;
			""".trimIndent()

		val UPDATE_AUTHOR: String = """
			UPDATE $TABLE_NAME 
			SET 
				$COLUMN_LOGIN = ?, 
				$COLUMN_PASSWORD = ?, 
				$COLUMN_FIRSTNAME = ?, 
				$COLUMN_LASTNAME = ? 
			WHERE $COLUMN_ID = ?;
			""".trimIndent()

		val DELETE_AUTHOR: String = """
			DELETE
			FROM $TABLE_NAME 
			WHERE $COLUMN_ID = ?;
			""".trimIndent()
	}
}