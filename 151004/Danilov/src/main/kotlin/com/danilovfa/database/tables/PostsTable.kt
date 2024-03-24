package com.danilovfa.database.tables

import com.danilovfa.database.DatabaseFactory.dbExec
import com.danilovfa.model.post.Post
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object PostsTable : Table(), PostsDao {
    override val tableName: String = "tbl_posts"

    val id = long("id").autoIncrement()
    val storyId = long("storyId")
    val content = largeText("name")
    override val primaryKey = PrimaryKey(id)

    override suspend fun getPosts(): List<Post> = dbExec {
        selectAll().map { it.toPost() }
    }

    override suspend fun getPost(id: Long): Post? = dbExec {
        select { this@PostsTable.id eq id }
            .map { it.toPost() }
            .singleOrNull()
    }

    override suspend fun insertPost(storyId: Long, content: String): Long = dbExec {
        insert {
            it[this@PostsTable.storyId] = storyId
            it[this@PostsTable.content] = content
        } get id
    }

    override suspend fun updatePost(id: Long, storyId: Long, content: String): Post? {
        dbExec {
            update(
                where = { this@PostsTable.id eq id }
            ) {
                it[this@PostsTable.storyId] = storyId
                it[this@PostsTable.content] = content
            }
        }

        return getPost(id)
    }

    override suspend fun deletePost(id: Long): Boolean = dbExec {
        deleteWhere { this@PostsTable.id eq id } > 0
    }

    override suspend fun canCreate(storyId: Long): Boolean =
        StoriesTable.storyExists(storyId)
}

private fun ResultRow.toPost(): Post =
    Post(
        id = this[PostsTable.id],
        storyId = this[PostsTable.storyId],
        content = this[PostsTable.content]
    )

interface PostsDao {
    suspend fun getPosts(): List<Post>
    suspend fun getPost(id: Long): Post?
    suspend fun insertPost(storyId: Long, content: String): Long
    suspend fun updatePost(id: Long, storyId: Long, content: String): Post?
    suspend fun deletePost(id: Long): Boolean
    suspend fun canCreate(storyId: Long): Boolean
}