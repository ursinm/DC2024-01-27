import {
    HttpException,
    Inject,
    Injectable,
    NotFoundException,
} from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { Author } from "src/entities/Author";
import { Repository } from "typeorm";
import { TweetRequestToCreate } from "../dto/request/TweetRequestToCreate";
import { TweetRequestToUpdate } from "../dto/request/TweetRequestToUpdate";
import { Tweet } from "../entities/Tweet";
import { Cache } from "cache-manager";
import { CACHE_MANAGER } from "@nestjs/cache-manager";
import { TTL, tweetCacheKeys } from "src/utils/redis/globalRedis";

@Injectable()
export class TweetService {
    constructor(
        @InjectRepository(Tweet) private tweetRepository: Repository<Tweet>,
        @InjectRepository(Author) private authorRepositry: Repository<Author>,
        @Inject(CACHE_MANAGER) private cacheManager: Cache,
    ) {}

    getAll(): Promise<Tweet[]> {
        return this.tweetRepository.find();
    }

    async createTweet(tweetDto: TweetRequestToCreate): Promise<Tweet> {
        let tweet = new Tweet();
        try {
            tweet = await this.tweetRepository.save(tweetDto);

            const allTweets = await this.getAll();
            this.cacheManager.set(tweetCacheKeys.tweets, allTweets, TTL);
        } catch (error) {
            throw new HttpException(`Tweet с title "${tweetDto.title}"`, 403);
        }
        return tweet;
    }

    getById(id: number): Promise<Tweet> {
        return this.tweetRepository.findOneBy({ id });
    }

    async deleteById(id: number): Promise<void> {
        try {
            await this.tweetRepository.findOneByOrFail({ id });

            let allTweets = await this.cacheManager.get<Tweet[]>(
                tweetCacheKeys.tweets,
            );
            allTweets = allTweets.filter((tweet) => tweet.id !== id);
            this.cacheManager.set(tweetCacheKeys.tweets, allTweets, TTL);
            this.cacheManager.del(tweetCacheKeys.tweets + id);
        } catch (error) {
            throw new NotFoundException(`Tweet with id: ${id} not found`);
        }
        await this.tweetRepository.delete(id);
    }

    async updateTweet(tweetDto: TweetRequestToUpdate): Promise<Tweet> {
        const tweet = await this.tweetRepository.findOneBy({ id: tweetDto.id });
        if (!tweet) {
            throw new NotFoundException(
                `Tweet with id: ${tweetDto.id} not found`,
            );
        }
        tweet.content = tweetDto.content;
        tweet.authorId = tweetDto.authorId;
        tweet.title = tweetDto.title;
        tweet.modified = new Date();

        let allTweets = await this.cacheManager.get<Tweet[]>(
            tweetCacheKeys.tweets,
        );
        allTweets = allTweets.map((tweet) => {
            if (tweet.id === tweetDto.id) {
                tweet.content = tweetDto.content;
                tweet.authorId = tweetDto.authorId;
                tweet.title = tweetDto.title;
            }
            return tweet;
        });
        this.cacheManager.set(tweetCacheKeys.tweets, allTweets, TTL);
        this.cacheManager.set(tweetCacheKeys.tweets + tweet.id, tweet, TTL);

        return this.tweetRepository.save(tweet);
    }
}
