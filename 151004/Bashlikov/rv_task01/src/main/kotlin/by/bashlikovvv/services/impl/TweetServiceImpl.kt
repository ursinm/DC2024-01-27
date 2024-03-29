package by.bashlikovvv.services.impl

import by.bashlikovvv.api.dto.mappers.CreateTweetDtoToTweetDtoMapper
import by.bashlikovvv.api.dto.mappers.UpdateTweetDtoToTweetDtoMapper
import by.bashlikovvv.api.dto.request.CreateTweetDto
import by.bashlikovvv.api.dto.request.UpdateTweetDto
import by.bashlikovvv.api.dto.response.TweetDto
import by.bashlikovvv.domain.repository.ITweetsRepository
import by.bashlikovvv.services.TweetService
import java.sql.Timestamp

class TweetServiceImpl(
    private val tweetRepository: ITweetsRepository
) : TweetService {
    override fun create(createTweetDto: CreateTweetDto): TweetDto? {
        val lastItemId = if (tweetRepository.data.isEmpty()) {
            -1
        } else {
            tweetRepository.getLastItem()?.id ?: return null
        }

        val timeStamp = Timestamp(System.currentTimeMillis())
        val savedEntity = tweetRepository.addItem(
            id = lastItemId + 1,
            item = CreateTweetDtoToTweetDtoMapper(lastItemId + 1, timeStamp).mapFromEntity(createTweetDto)
        )

        return savedEntity
    }

    override fun getAll(): List<TweetDto?> {
        return tweetRepository.data.map { it.second }
    }

    override fun getById(tweetId: Long): TweetDto? {
        return tweetRepository.getItemById(tweetId)?.second
    }

    override fun getByEditorId(editorId: Long): TweetDto? {
        return tweetRepository.data.find { it.second.editorId == editorId }?.second
    }

    override fun update(tweetId: Long, updateTweetDto: UpdateTweetDto): TweetDto? {
        val tweetDto = tweetRepository.getItemById(tweetId)?.second
        tweetDto ?: return null

        val modified = Timestamp(System.currentTimeMillis())
        val savedEntity = tweetRepository.addItem(
            id = tweetId,
            item = UpdateTweetDtoToTweetDtoMapper(tweetDto, modified).mapFromEntity(updateTweetDto)
        )

        return savedEntity
    }

    override fun delete(tweetId: Long): Boolean {
        return tweetRepository.removeItem(tweetId)
    }
}