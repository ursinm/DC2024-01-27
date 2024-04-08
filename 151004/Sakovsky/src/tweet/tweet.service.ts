import { HttpException, Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Author } from 'src/entities/Author';
import { Repository } from 'typeorm';
import { TweetRequestToCreate } from '../dto/request/TweetRequestToCreate';
import { TweetRequestToUpdate } from '../dto/request/TweetRequestToUpdate';
import { Tweet } from '../entities/Tweet';

@Injectable()
export class TweetService {
    constructor(@InjectRepository(Tweet) private tweetRepository: Repository<Tweet>,
                @InjectRepository(Author) private authorRepositry: Repository<Author>){}

    getAll(): Promise<Tweet[]>{
        return this.tweetRepository.find();
    }

    async createTweet(tweetDto: TweetRequestToCreate): Promise<Tweet>{
        let tweet = new Tweet();
        try {
            tweet = await this.tweetRepository.save(tweetDto);
        } catch (error) {
            throw new HttpException(`Tweet с title "${tweetDto.title}"`, 403)
        }
        return tweet;
    }

    getById(id: number): Promise<Tweet>{
        return this.tweetRepository.findOneBy({id});
    }

    async deleteById(id: number): Promise<void>{
        try {
            await this.tweetRepository.findOneByOrFail({id});
        } catch (error) {
            throw new NotFoundException(`Tweet with id: ${id} not found`)
        }
        await this.tweetRepository.delete(id);
    }

    async updateTweet( tweerDto: TweetRequestToUpdate): Promise<Tweet>{
        const tweet = await this.tweetRepository.findOneBy({id: tweerDto.id})
        if (!tweet){
            throw new NotFoundException(`Tweet with id: ${tweerDto.id} not found`)
        }
        tweet.content = tweerDto.content;
        tweet.authorId = tweerDto.authorId;
        tweet.title = tweerDto.title;
        tweet.modified = new Date();//.toISOString().slice(0, 19).replace("T", " ")

        return this.tweetRepository.save(tweet);
    }
}
