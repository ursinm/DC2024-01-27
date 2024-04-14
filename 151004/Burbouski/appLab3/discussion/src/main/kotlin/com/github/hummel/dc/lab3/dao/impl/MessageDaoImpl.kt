package com.github.hummel.dc.lab3.dao.impl

import com.github.hummel.dc.lab3.bean.Message
import com.github.hummel.dc.lab3.dao.MessageDao
import com.github.hummel.dc.lab3.database.Messages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.ResultSet

class MessageDaoImpl(private val session: com.datastax.driver.core.Session) : MessageDao {
	init {
		session.execute("DROP TABLE distcomp.tbl_message_by_country")
		session.execute("CREATE TABLE distcomp.tbl_message_by_country (country text, issue_id bigint, id bigint, content text, PRIMARY KEY ((country), issue_id, id))")
	}

	private fun ResultSet.getString(value: Messages): String = getString("$value")
	private fun ResultSet.getLong(value: Messages): Long = getLong("$value")

	override suspend fun create(item: Message): Long = withContext(Dispatchers.IO) {
		0
	}

	override suspend fun deleteById(id: Long): Int = withContext(Dispatchers.IO) {
		0
	}

	override suspend fun getAll(): List<Message> = withContext(Dispatchers.IO) {
		mutableListOf()
	}

	override suspend fun getById(id: Long): Message = withContext(Dispatchers.IO) {
		Message(0, "", 0, "")
	}

	override suspend fun update(item: Message): Int = withContext(Dispatchers.IO) {
		0
	}
}