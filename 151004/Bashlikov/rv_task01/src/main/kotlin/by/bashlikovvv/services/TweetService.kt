package by.bashlikovvv.services

import by.bashlikovvv.api.dto.request.CreateTweetDto
import by.bashlikovvv.api.dto.request.UpdateTweetDto
import by.bashlikovvv.api.dto.response.TweetDto

interface TweetService {

    fun create(createTweetDto: CreateTweetDto): TweetDto?

    fun getAll(): List<TweetDto?>

    fun getById(tweetId: Long): TweetDto?

    fun getByEditorId(editorId: Long): TweetDto?

    fun update(tweetId: Long, updateTweetDto: UpdateTweetDto): TweetDto?

    fun delete(tweetId: Long): Boolean

}