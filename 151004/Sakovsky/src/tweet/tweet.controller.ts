import { Body, Controller, Delete, Get, HttpCode, NotFoundException, Param, ParseIntPipe, Post, Put, UsePipes, ValidationPipe } from '@nestjs/common';
import { TweetRequestToCreate } from '../dto/request/TweetRequestToCreate';
import { TweetRequestToUpdate } from '../dto/request/TweetRequestToUpdate';
import { TweetResponseTo } from '../dto/response/TweetResponseTo';
import { TweetService } from './tweet.service';

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
    async getById(@Param('id', ParseIntPipe) id: number): Promise<TweetResponseTo>{
        const tweetDto = new TweetResponseTo();
        const tweet = await this.tweetService.getById(id);
        if(!tweet){
            throw new NotFoundException('Tweet not found')
        }
        tweetDto.id = tweet.id;
        tweetDto.authorId = tweet.authorId;
        tweetDto.content = tweet.content;
        tweetDto.title = tweet.title;
        tweetDto.created = tweet.created;
        tweetDto.modified = tweet.modified;
        return tweetDto;
    }

    @UsePipes(new ValidationPipe())
    @Post()
    async createTweet(@Body() tweetReqDto: TweetRequestToCreate): Promise<TweetResponseTo>{
        const tweetRespDto = new TweetResponseTo();
        const tweet = await this.tweetService.createTweet(tweetReqDto);
        tweetRespDto.id = tweet.id;
        tweetRespDto.authorId = tweet.authorId;
        tweetRespDto.content = tweet.content;
        tweetRespDto.title = tweet.title;
        tweetRespDto.created = tweet.created;
        tweetRespDto.modified = tweet.modified;
        return tweetRespDto;
    }

    @UsePipes(new ValidationPipe())
    @Put()
    async updateTweet(@Body() tweetReqDto: TweetRequestToUpdate): Promise<TweetResponseTo>{
        const tweet = await this.tweetService.updateTweet( tweetReqDto);

        const tweetRespDto = new TweetResponseTo();
        tweetRespDto.id = tweet.id;
        tweetRespDto.authorId = tweet.authorId;
        tweetRespDto.content = tweet.content;
        tweetRespDto.title = tweet.title;
        tweetRespDto.created = tweet.created;
        tweetRespDto.modified = tweet.modified;
        return tweetRespDto;
    }

    @HttpCode(204)
    @Delete(':id')
    deleteTweet(@Param('id', ParseIntPipe) id: number): Promise<void>{
        return this.tweetService.deleteById(id);
    }
}
