import {
  HttpException,
  Inject,
  Injectable,
  OnModuleInit,
} from "@nestjs/common";
import { DELAY, KAFKA_KEYS, REDIS_KEYS, TOPICS } from "src/constants";
import { Message } from "src/schemas";
import { ConsumerService } from "../kafka/consumer.service";
import { ProducerService } from "../kafka/producer.service";
import { StoriesService } from "../stories/stories.service";
import { CreateMessageDto } from "./dto/create-Message.dto";
import { UpdateMessageDto } from "./dto/update-Message.dto";
import { CACHE_MANAGER } from "@nestjs/cache-manager";
import { Cache } from "cache-manager";

@Injectable()
export class MessagesService implements OnModuleInit {
  constructor(
    private storiesService: StoriesService,
    private producerService: ProducerService,
    private consumerService: ConsumerService,
    @Inject(CACHE_MANAGER) private cacheManager: Cache
  ) {}

  private result: any = undefined;

  async onModuleInit() {
    await this.consumerService.consume(
      { topics: [TOPICS.OUT_TOPIC] },
      {
        eachMessage: async ({ message }) => {
          this.result = JSON.parse(message.value?.toString() as string);
        },
      }
    );
  }

  async create(createMessageDto: CreateMessageDto): Promise<Message> {
    // check if story exists
    await this.storiesService.findOne(createMessageDto.storyId);

    const id = this.generateId();
    const createdEntity = { id, ...createMessageDto };
    await this.producerService.produce(
      this.createKafkaMessage(KAFKA_KEYS.CREATE, createdEntity)
    );

    await this.sleep();

    await this.cacheManager.del(REDIS_KEYS.MESSAGES_GET_ALL);

    return createdEntity;
  }

  async findAll(): Promise<Message[]> {
    const cache = await this.cacheManager.get<Message[]>(
      REDIS_KEYS.MESSAGES_GET_ALL
    );
    if (cache) {
      return cache;
    }

    let result = [];
    await this.producerService.produce(
      this.createKafkaMessage(KAFKA_KEYS.GET_ALL, "")
    );

    await this.sleep();

    if (this.result) {
      result = this.result;
      await this.cacheManager.set(REDIS_KEYS.MESSAGES_GET_ALL, result);
      this.result = undefined;
    }

    return result;
  }

  async findOne(id: number): Promise<Message> {
    const cache = await this.cacheManager.get<Message>(
      this.getEntityCacheKey(id)
    );
    if (cache) {
      return cache;
    }

    let result;

    await this.producerService.produce(
      this.createKafkaMessage(KAFKA_KEYS.GET_ONE, id)
    );

    await this.sleep();

    if (this.result) {
      result = this.result;
      this.result = undefined;
    }

    if (result && "error" in result) {
      throw new HttpException(result.error.message, result.error.code);
    }

    await this.cacheManager.set(this.getEntityCacheKey(id), result);

    return result;
  }

  async update(updateMessageDto: UpdateMessageDto): Promise<Message> {
    // check if message exists
    await this.findOne(updateMessageDto.id);

    // check if story exists
    await this.storiesService.findOne(updateMessageDto.storyId);

    let result;

    await this.producerService.produce(
      this.createKafkaMessage(KAFKA_KEYS.UPDATE, updateMessageDto)
    );

    await this.sleep();

    if (this.result) {
      result = this.result;
      await this.cacheManager.del(REDIS_KEYS.MESSAGES_GET_ALL);
      await this.cacheManager.del(this.getEntityCacheKey(updateMessageDto.id));
      this.result = undefined;
    }

    return result;
  }

  async remove(id: number): Promise<void> {
    // check if message exists
    await this.findOne(id);

    await this.producerService.produce(
      this.createKafkaMessage(KAFKA_KEYS.DELETE, id)
    );

    await this.sleep();

    await this.cacheManager.del(REDIS_KEYS.MESSAGES_GET_ALL);
    await this.cacheManager.del(this.getEntityCacheKey(id));
  }

  private generateId = (): number => {
    return Math.round(Math.random() * 100000);
  };

  private createKafkaMessage(key: string, value: any) {
    return {
      topic: TOPICS.IN_TOPIC,
      messages: [{ key, value: JSON.stringify(value) }],
    };
  }

  private sleep() {
    return new Promise((resolve) => setTimeout(resolve, DELAY));
  }

  getEntityCacheKey(id: number) {
    return `${REDIS_KEYS.MESSAGES_GET_ONE}.${id}`;
  }
}
