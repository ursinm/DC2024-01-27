package com.github.hummel.dc.lab4.database

enum class Issues(private val col: String) {
	TABLE_NAME(
		"issues"
	),
	COLUMN_ID(
		"issues_id"
	),
	COLUMN_AUTHOR_ID(
		"issues_author_id"
	),
	COLUMN_TITLE(
		"issues_title"
	),
	COLUMN_CONTENT(
		"issues_content"
	),
	COLUMN_CREATED(
		"issues_created"
	),
	COLUMN_MODIFIED(
		"issues_modified"
	);

	override fun toString(): String = col

	companion object {
		val CREATE_TABLE_ISSUES: String = """
			CREATE TABLE $TABLE_NAME (
				$COLUMN_ID SERIAL PRIMARY KEY,
				$COLUMN_AUTHOR_ID BIGINT,
				$COLUMN_TITLE VARCHAR(64) UNIQUE,
				$COLUMN_CONTENT VARCHAR(2048),
				$COLUMN_CREATED TIMESTAMP,
				$COLUMN_MODIFIED TIMESTAMP,
				FOREIGN KEY($COLUMN_AUTHOR_ID) REFERENCES ${Authors.TABLE_NAME} (${Authors.COLUMN_ID}) ON UPDATE NO ACTION
			);
			""".trimIndent()

		val INSERT_ISSUE: String = """
			INSERT INTO $TABLE_NAME (
				$COLUMN_AUTHOR_ID,
				$COLUMN_TITLE,
				$COLUMN_CONTENT,
				$COLUMN_CREATED,
				$COLUMN_MODIFIED
			) VALUES (?, ?, ?, ?, ?);
			""".trimIndent()

		val SELECT_ISSUE_BY_ID: String = """
			SELECT
				$COLUMN_AUTHOR_ID,
				$COLUMN_TITLE,
				$COLUMN_CONTENT,
				$COLUMN_CREATED,
				$COLUMN_MODIFIED
			FROM $TABLE_NAME
			WHERE $COLUMN_ID = ?;
			""".trimIndent()

		val SELECT_ISSUES: String = """
			SELECT
				$COLUMN_ID,
				$COLUMN_AUTHOR_ID,
				$COLUMN_TITLE,
				$COLUMN_CONTENT,
				$COLUMN_CREATED,
				$COLUMN_MODIFIED
			FROM $TABLE_NAME;
			""".trimIndent()

		val UPDATE_ISSUE: String = """
			UPDATE $TABLE_NAME
			SET
				$COLUMN_AUTHOR_ID = ?,
				$COLUMN_TITLE = ?,
				$COLUMN_CONTENT = ?,
				$COLUMN_CREATED = ?,
				$COLUMN_MODIFIED = ?
			WHERE $COLUMN_ID = ?;
			""".trimIndent()

		val DELETE_ISSUE: String = """
			DELETE FROM $TABLE_NAME
			WHERE $COLUMN_ID = ?;
			""".trimIndent()
	}
}