import { Module } from "@nestjs/common";
import { ConfigModule } from "@nestjs/config";
import { MessagesModule } from "./modules/messages/messages.module";

@Module({
  imports: [ConfigModule.forRoot(), MessagesModule],
  controllers: [],
  providers: [],
})
export class AppModule {}
