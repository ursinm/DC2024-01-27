import { Module } from '@nestjs/common';
import { TweetService } from './tweet.service';
import { TweetController } from './tweet.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Tweet } from '../entities/Tweet';
import { Author } from 'src/entities/Author';

@Module({
  imports: [TypeOrmModule.forFeature([Tweet, Author])],
  providers: [TweetService],
  controllers: [TweetController]
})
export class TweetModule {}
