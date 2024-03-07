package com.github.hummel.dc.lab2.database

enum class Messages(private val col: String) {
	TABLE_NAME(
		"messages"
	),
	COLUMN_ID(
		"messages_id"
	),
	COLUMN_ISSUE_ID(
		"messages_issue_id"
	),
	COLUMN_CONTENT(
		"messages_content"
	);

	override fun toString(): String = col

	companion object {
	}
}