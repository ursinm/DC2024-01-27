import { CacheModule } from "@nestjs/cache-manager";
import { Module } from "@nestjs/common";
import { ConfigModule, ConfigService } from "@nestjs/config";
import { TypeOrmModule } from "@nestjs/typeorm";
import { redisStore } from "cache-manager-redis-yet";
import { DataSource } from "typeorm";
import { AppController } from "./app.controller";
import { AppService } from "./app.service";
import { AuthorModule } from "./author/author.module";
import { CommentModule } from "./comment/comment.module";
import { Author } from "./entities/Author";
import { Comment } from "./entities/Comment";
import { Marker } from "./entities/Marker";
import { Tweet } from "./entities/Tweet";
import { MarkerModule } from "./marker/marker.module";
import redisConfig from "./redisConfig";
import { TweetModule } from "./tweet/tweet.module";

@Module({
    imports: [
        TypeOrmModule.forRoot({
            type: "postgres",
            host: "localhost",
            port: 5432,
            username: "postgres",
            password: "postgres",
            database: "dc",
            schema: "distcomp",
            entities: [Author, Tweet, Comment, Marker],
            synchronize: true,
        }),
        AuthorModule,
        TweetModule,
        CommentModule,
        MarkerModule,
        ConfigModule.forRoot({
            load: [redisConfig],
            isGlobal: true,
        }),
        CacheModule.registerAsync({
            isGlobal: true,
            imports: [ConfigModule],
            inject: [ConfigService],
            useFactory: async (config) => {
                const store = await redisStore({
                    socket: {
                        host: config.get("redis.host"),
                        port: config.get("redis.port"),
                    },
                    ttl: 10 * 1000,//10 seconds
                });
                return {store}
            },
        }),
    ],
    controllers: [AppController],
    providers: [AppService],
})
export class AppModule {
    constructor(private dataSource: DataSource) {}
}
