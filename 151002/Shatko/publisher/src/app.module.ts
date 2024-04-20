import { Module } from "@nestjs/common";
import { ConfigModule, ConfigService } from "@nestjs/config";
import { DrizzleModule } from "./modules/drizzle/drizzle.module";
import { EditorsModule } from "./modules/editors/editors.module";
import { StoriesModule } from "./modules/stories/stories.module";
import { MessagesModule } from "./modules/messages/messages.module";
import { TagsModule } from "./modules/tags/tags.module";
import { KafkaModule } from "./modules/kafka/kafka.module";
import { CacheModule } from "@nestjs/cache-manager";
import { redisStore } from "cache-manager-redis-yet";

@Module({
  imports: [
    ConfigModule.forRoot(),
    CacheModule.register({
      store: redisStore,
      isGlobal: true,
    }),
    DrizzleModule,
    EditorsModule,
    StoriesModule,
    MessagesModule,
    TagsModule,
    KafkaModule,
  ],
  controllers: [],
  providers: [],
})
export class AppModule {}
