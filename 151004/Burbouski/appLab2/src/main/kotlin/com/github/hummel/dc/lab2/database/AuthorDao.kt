package com.github.hummel.dc.lab2.database

import com.github.hummel.dc.lab2.bean.Author
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.Statement

private const val TABLE_NAME: String = "authors"
private const val COLUMN_ID: String = "authors_id"
private const val COLUMN_LOGIN: String = "authors_login"
private const val COLUMN_PASSWORD: String = "authors_password"
private const val COLUMN_FIRSTNAME: String = "authors_firstname"
private const val COLUMN_LASTNAME: String = "authors_lastname"

private val CREATE_TABLE_AUTHORS = """
	CREATE TABLE $TABLE_NAME
	(
		$COLUMN_ID SERIAL PRIMARY KEY, 
		$COLUMN_LOGIN VARCHAR(64) UNIQUE, 
		$COLUMN_PASSWORD VARCHAR(128), 
		$COLUMN_FIRSTNAME VARCHAR(64), 
		$COLUMN_LASTNAME VARCHAR(64)
	);
	""".trimIndent()

private val INSERT_AUTHOR = """
	INSERT
	INTO $TABLE_NAME
	(
		$COLUMN_LOGIN, 
		$COLUMN_PASSWORD, 
		$COLUMN_FIRSTNAME, 
		$COLUMN_LASTNAME
	)
	VALUES (?, ?, ?, ?);
	""".trimIndent()

private val SELECT_AUTHOR_BY_ID = """
	SELECT
		$COLUMN_LOGIN, 
		$COLUMN_PASSWORD, 
		$COLUMN_FIRSTNAME, 
		$COLUMN_LASTNAME 
	FROM $TABLE_NAME 
	WHERE $COLUMN_ID = ?;
	""".trimIndent()

private val SELECT_AUTHORS = """
	SELECT 
		$COLUMN_ID, 
		$COLUMN_LOGIN, 
		$COLUMN_PASSWORD, 
		$COLUMN_FIRSTNAME, 
		$COLUMN_LASTNAME
	FROM $TABLE_NAME;
	""".trimIndent()

private val UPDATE_AUTHOR = """
	UPDATE $TABLE_NAME 
	SET 
		$COLUMN_LOGIN = ?, 
		$COLUMN_PASSWORD = ?, 
		$COLUMN_FIRSTNAME = ?, 
		$COLUMN_LASTNAME = ? 
	WHERE $COLUMN_ID = ?;
	""".trimMargin()

private val DELETE_AUTHOR = """
	DELETE
	FROM $TABLE_NAME 
	WHERE $COLUMN_ID = ?;
	""".trimIndent()

class AuthorDao(private val connection: Connection) {
	init {
		val statement = connection.createStatement()
		statement.executeUpdate(CREATE_TABLE_AUTHORS)
	}

	suspend fun create(authorEntity: Author): Long = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(INSERT_AUTHOR, Statement.RETURN_GENERATED_KEYS)
		statement.apply {
			setString(1, authorEntity.login)
			setString(2, authorEntity.password)
			setString(3, authorEntity.firstname)
			setString(4, authorEntity.lastname)
			executeUpdate()
		}

		val generatedKeys = statement.generatedKeys
		if (generatedKeys.next()) {
			return@withContext generatedKeys.getLong(1)
		} else {
			throw Exception("Unable to retrieve the id of the newly inserted item.")
		}
	}

	suspend fun read(id: Long): Author = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(SELECT_AUTHOR_BY_ID)
		statement.setLong(1, id)

		val resultSet = statement.executeQuery()
		if (resultSet.next()) {
			val login = resultSet.getString(COLUMN_LOGIN)
			val password = resultSet.getString(COLUMN_PASSWORD)
			val firstname = resultSet.getString(COLUMN_FIRSTNAME)
			val lastname = resultSet.getString(COLUMN_LASTNAME)
			return@withContext Author(
				id = id, login = login, password = password, firstname = firstname, lastname = lastname
			)
		} else {
			throw Exception("Item record not found.")
		}
	}

	suspend fun readAll(): List<Author?> = withContext(Dispatchers.IO) {
		val result = mutableListOf<Author>()
		val statement = connection.prepareStatement(SELECT_AUTHORS)

		val resultSet = statement.executeQuery()
		while (resultSet.next()) {
			val id = resultSet.getLong(COLUMN_ID)
			val login = resultSet.getString(COLUMN_LOGIN)
			val password = resultSet.getString(COLUMN_PASSWORD)
			val firstname = resultSet.getString(COLUMN_FIRSTNAME)
			val lastname = resultSet.getString(COLUMN_LASTNAME)
			result.add(
				Author(
					id = id, login = login, password = password, firstname = firstname, lastname = lastname
				)
			)
		}

		result
	}

	suspend fun update(id: Long, authorEntity: Author): Int = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(UPDATE_AUTHOR)
		statement.apply {
			setString(1, authorEntity.login)
			setString(2, authorEntity.password)
			setString(3, authorEntity.firstname)
			setString(4, authorEntity.lastname)
			setLong(5, id)
		}

		return@withContext try {
			statement.executeUpdate()
		} catch (e: Exception) {
			throw Exception("Can not modify item record.")
		}
	}

	suspend fun delete(id: Long): Int = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(DELETE_AUTHOR)
		statement.setLong(1, id)

		return@withContext try {
			statement.executeUpdate()
		} catch (e: Exception) {
			throw Exception("Can not delete item record.")
		}
	}

}