package com.danilovfa.database.tables

import com.danilovfa.database.DatabaseFactory.dbExec
import com.danilovfa.model.user.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object UsersTable : Table(), UsersDao {
    override val tableName: String = "tbl_users"

    val id = long("id").autoIncrement()
    val login = text("login")
    val password = text("password")
    val firstName = text("firstName")
    val lastName = text("lastName")
    override val primaryKey = PrimaryKey(id)

    override suspend fun getUsers(): List<User> = dbExec {
        selectAll().map { it.toUser() }
    }

    override suspend fun getUser(id: Long): User? = dbExec {
        select { this@UsersTable.id eq id }
            .map { it.toUser() }
            .singleOrNull()
    }

    override suspend fun insertUser(login: String, password: String, firstName: String, lastName: String): Long =
        dbExec {
            insert {
                it[this@UsersTable.login] = login
                it[this@UsersTable.password] = password
                it[this@UsersTable.firstName] = firstName
                it[this@UsersTable.lastName] = lastName
            } get id
        }

    override suspend fun updateUser(
        id: Long,
        login: String,
        password: String,
        firstName: String,
        lastName: String
    ): User? = dbExec {
        update(
            where = { this@UsersTable.id eq id }
        ) {
            it[this@UsersTable.login] = login
            it[this@UsersTable.password] = password
            it[this@UsersTable.firstName] = firstName
            it[this@UsersTable.lastName] = lastName
        }
        select { this@UsersTable.id eq id }
            .map { it.toUser() }
            .singleOrNull()
    }

    override suspend fun deleteUser(id: Long): Boolean = dbExec {
        deleteWhere { this@UsersTable.id eq id } > 0
    }

    override suspend fun userExists(login: String): Boolean = dbExec {
        select { this@UsersTable.login eq login }
            .map { it.toUser() }
            .singleOrNull() != null
    }

    override suspend fun userExists(id: Long): Boolean = dbExec {
        select { this@UsersTable.id eq id }
            .map { it.toUser() }
            .singleOrNull() != null
    }
}

private fun ResultRow.toUser() =
    User(
        id = this[UsersTable.id],
        login = this[UsersTable.login],
        password = this[UsersTable.password],
        firstName = this[UsersTable.firstName],
        lastName = this[UsersTable.lastName]
    )

interface UsersDao {
    suspend fun getUsers(): List<User>
    suspend fun getUser(id: Long): User?
    suspend fun insertUser(login: String, password: String, firstName: String, lastName: String): Long
    suspend fun updateUser(id: Long, login: String, password: String, firstName: String, lastName: String): User?
    suspend fun deleteUser(id: Long): Boolean
    suspend fun userExists(login: String): Boolean
    suspend fun userExists(id: Long): Boolean
}