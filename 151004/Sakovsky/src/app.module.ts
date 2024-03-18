import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { DataSource } from 'typeorm';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { AuthorModule } from './author/author.module';
import { CommentModule } from './comment/comment.module';
import { Author } from './entities/Author';
import { Comment } from './entities/Comment';
import { Marker } from './entities/Marker';
import { Tweet } from './entities/Tweet';
import { MarkerModule } from './marker/marker.module';
import { TweetModule } from './tweet/tweet.module';

@Module({
  imports: [
    // TypeOrmModule.forRoot({
    //   type: 'mysql',
    //   host: 'localhost',
    //   port: 3306,
    //   username: 'root',
    //   password: 'S@ky12345',
    //   database: 'dc',
    //   entities: [Author, Tweet, Comment, Marker],
    //   synchronize: false,
    // }),
    TypeOrmModule.forRoot({
      type: 'postgres',
      host: 'localhost',
      port: 5432,
      username: 'postgres',
      password: 'postgres',
      database: 'dc',
      schema: 'distcomp',
      entities: [Author, Tweet, Comment, Marker],
      synchronize: true,
    }),
    AuthorModule,
    TweetModule,
    CommentModule,
    MarkerModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {
  constructor(private dataSource: DataSource) {}
}
