import { Module } from "@nestjs/common";
import { MessagesService } from "./messages.service";
import { MessagesController } from "./messages.controller";
import { CassandraProvider } from "../cassandra/cassandra.provider";

@Module({
  controllers: [MessagesController],
  providers: [MessagesService, CassandraProvider],
})
export class MessagesModule {}
