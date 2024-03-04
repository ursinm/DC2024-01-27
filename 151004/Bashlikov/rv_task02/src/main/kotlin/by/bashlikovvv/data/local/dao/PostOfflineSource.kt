package by.bashlikovvv.data.local.dao

import by.bashlikovvv.data.local.contract.DatabaseContract.PostsTable
import by.bashlikovvv.data.local.contract.DatabaseContract.TweetsTable
import by.bashlikovvv.data.local.model.PostEntity
import by.bashlikovvv.domain.exception.DataSourceExceptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.Statement

class PostOfflineSource(private val connection: Connection) {

    companion object {
        /* Create posts table */
        private const val CREATE_TABLE_POSTS =
            "CREATE TABLE ${PostsTable.TABLE_NAME} " +
            "(" +
                    "${PostsTable.COLUMN_ID} SERIAL PRIMARY KEY, " +
                    "${PostsTable.COLUMN_TWEET_ID} BIGINT, " +
                    "${PostsTable.COLUMN_CONTENT} VARCHAR(2048), " +
                    "FOREIGN KEY (${PostsTable.COLUMN_TWEET_ID}) REFERENCES ${TweetsTable.TABLE_NAME} (${TweetsTable.COLUMN_ID}) ON UPDATE NO ACTION" +
            ");"
        /* Add a new post at the table */
        private const val INSERT_POST =
            "INSERT INTO ${PostsTable.TABLE_NAME} " +
            "(" +
                    "${PostsTable.COLUMN_TWEET_ID}, " +
                    PostsTable.COLUMN_CONTENT +
            ") VALUES (?, ?);"
        /* Get post by id */
        private const val SELECT_POST_BY_ID =
            "SELECT " +
                    "${PostsTable.COLUMN_TWEET_ID}, " +
                    "${PostsTable.COLUMN_CONTENT} " +
            "FROM ${PostsTable.TABLE_NAME} " +
            "WHERE ${PostsTable.COLUMN_ID} = ?;"
        /* Get all posts */
        private const val SELECT_POSTS =
            "SELECT " +
                    "${PostsTable.COLUMN_ID}, " +
                    "${PostsTable.COLUMN_TWEET_ID}, " +
                    "${PostsTable.COLUMN_CONTENT} " +
            "FROM ${PostsTable.TABLE_NAME};"
        /* Update exists post by id */
        private const val UPDATE_POST =
            "UPDATE ${PostsTable.TABLE_NAME} " +
            "SET " +
                    "${PostsTable.COLUMN_TWEET_ID} = ?, " +
                    "${PostsTable.COLUMN_CONTENT} = ? " +
            "WHERE ${PostsTable.COLUMN_ID} = ?;"
        /* Delete post from table */
        private const val DELETE_POST =
            "DELETE FROM ${PostsTable.TABLE_NAME} " +
            "WHERE ${PostsTable.COLUMN_ID} = ?;"
    }

    init {
        val statement = connection.createStatement()
        statement.executeUpdate(CREATE_TABLE_POSTS)
    }

    suspend fun create(postEntity: PostEntity): Long = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(INSERT_POST, Statement.RETURN_GENERATED_KEYS)
        statement.apply {
            setLong(1, postEntity.tweetId)
            setString(2, postEntity.content)
            executeUpdate()
        }

        val generatedKeys = statement.generatedKeys
        if (generatedKeys.next()) {
            return@withContext generatedKeys.getLong(1)
        } else {
            throw DataSourceExceptions.RecordCreationException("Unable to retrieve the id of the newly inserted post")
        }
    }

    suspend fun read(id: Long): PostEntity = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_POST_BY_ID)
        statement.setLong(1, id)

        val resultSet = statement.executeQuery()
        if (resultSet.next()) {
            val tweetId = resultSet.getLong(PostsTable.COLUMN_TWEET_ID)
            val content = resultSet.getString(PostsTable.COLUMN_CONTENT)
            return@withContext PostEntity(
                id = id,
                tweetId = tweetId,
                content = content
            )
        } else {
            throw DataSourceExceptions.RecordNotFoundException("Post record not found")
        }
    }

    suspend fun readAll(): List<PostEntity> = withContext(Dispatchers.IO) {
        val result = mutableListOf<PostEntity>()
        val statement = connection.prepareStatement(SELECT_POSTS)

        val resultSet = statement.executeQuery()
        while (resultSet.next()) {
            val id = resultSet.getLong(PostsTable.COLUMN_ID)
            val tweetId = resultSet.getLong(PostsTable.COLUMN_TWEET_ID)
            val content = resultSet.getString(PostsTable.COLUMN_CONTENT)
            result.add(
                PostEntity(
                    id = id,
                    tweetId = tweetId,
                    content = content
                )
            )
        }

        result
    }

    suspend fun update(id: Long, postEntity: PostEntity) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(UPDATE_POST)
        statement.apply {
            setLong(1, postEntity.tweetId)
            setString(2, postEntity.content)
            setLong(3, id)
        }

        return@withContext try {
            statement.executeUpdate()
        } catch (e: Exception) {
            throw DataSourceExceptions.RecordModificationException("Can not modify post record")
        }
    }

    suspend fun delete(id: Long) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(DELETE_POST)
        statement.setLong(1, id)

        return@withContext try {
            statement.executeUpdate()
        } catch (e: Exception) {
            throw DataSourceExceptions.RecordDeletionException("Can not delete post record")
        }
    }

}