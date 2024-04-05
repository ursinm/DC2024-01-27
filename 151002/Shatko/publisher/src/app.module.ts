import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { DrizzleModule } from './modules/drizzle/drizzle.module';
import { EditorsModule } from './modules/editors/editors.module';
import { StoriesModule } from './modules/stories/stories.module';
import { MessagesModule } from './modules/messages/messages.module';
import { TagsModule } from './modules/tags/tags.module';

@Module({
  imports: [
    ConfigModule.forRoot(),
    DrizzleModule,
    EditorsModule,
    StoriesModule,
    MessagesModule,
    TagsModule,
  ],
  controllers: [],
  providers: [],
})
export class AppModule {}
