import { CacheInterceptor } from '@nestjs/cache-manager';
import { Body, Controller, Delete, Get, HttpCode, NotFoundException, Param, ParseIntPipe, Post, Put, UseInterceptors, UsePipes, ValidationPipe } from '@nestjs/common';
import { Tweet } from 'src/entities/Tweet';
import { TweetRequestToCreate } from '../dto/request/TweetRequestToCreate';
import { TweetRequestToUpdate } from '../dto/request/TweetRequestToUpdate';
import { TweetResponseTo } from '../dto/response/TweetResponseTo';
import { TweetService } from './tweet.service';

@UseInterceptors(CacheInterceptor)
@Controller('tweets')
export class TweetController {
    constructor(private readonly tweetService: TweetService ){}

    @Get()
    async getAll(): Promise<TweetResponseTo[]>{
        const tweetsDto: TweetResponseTo[]= [];
        const tweets = await this.tweetService.getAll();
        for (const tweet of tweets) {
            const tweetDto = new TweetResponseTo();
            tweetDto.id = tweet.id;
            tweetDto.authorId = tweet.authorId;
            tweetDto.content = tweet.content;
            tweetDto.title = tweet.title;
            tweetDto.created = tweet.created;
            tweetDto.modified = tweet.modified;
            tweetsDto.push(tweetDto);
        }
        return tweetsDto
    }

    @Get(':id')
    async getById(@Param('id', ParseIntPipe) id: number): Promise<Tweet>{
        const tweet = await this.tweetService.getById(id);
        if(!tweet){
            throw new NotFoundException('Tweet not found')
        }
        return tweet;
    }

    @UsePipes(new ValidationPipe())
    @Post()
    async createTweet(@Body() tweetReqDto: TweetRequestToCreate): Promise<Tweet>{
        const tweet = await this.tweetService.createTweet(tweetReqDto);
        return tweet;
    }

    @UsePipes(new ValidationPipe())
    @Put()
    async updateTweet(@Body() tweetReqDto: TweetRequestToUpdate): Promise<Tweet>{
        const tweet = await this.tweetService.updateTweet( tweetReqDto);
        return tweet;
    }

    @HttpCode(204)
    @Delete(':id')
    deleteTweet(@Param('id', ParseIntPipe) id: number): Promise<void>{
        return this.tweetService.deleteById(id);
    }
}
