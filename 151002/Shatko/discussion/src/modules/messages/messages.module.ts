import { Module } from "@nestjs/common";
import { MessagesService } from "./messages.service";
import { MessagesController } from "./messages.controller";
import { CassandraProvider } from "../cassandra/cassandra.provider";
import { ConsumerService } from "../kafka/consumer.service";
import { ProducerService } from "../kafka/producer.service";

@Module({
  controllers: [MessagesController],
  providers: [
    MessagesService,
    CassandraProvider,
    ConsumerService,
    ProducerService,
  ],
})
export class MessagesModule {}
