import {
  HttpException,
  HttpStatus,
  Inject,
  Injectable,
  OnModuleInit,
} from "@nestjs/common";
import { CreateMessageDto } from "./dto/create-Message.dto";
import { UpdateMessageDto } from "./dto/update-Message.dto";
import { KAFKA_KEYS, PROVIDERS, TOPICS } from "src/constants";
import { ConsumerService } from "../kafka/consumer.service";
import { ProducerService } from "../kafka/producer.service";

@Injectable()
export class MessagesService implements OnModuleInit {
  constructor(
    @Inject(PROVIDERS.CASSANDRA) private db: any,
    private consumerService: ConsumerService,
    private producerService: ProducerService
  ) {}

  async onModuleInit() {
    await this.consumerService.consume(
      { topics: [TOPICS.IN_TOPIC] },
      {
        eachMessage: async ({ message }) => {
          const parsedValue = JSON.parse(
            message.value?.toString() as string
          ) as any;
          switch (message.key?.toString()) {
            case KAFKA_KEYS.CREATE:
              const { id: createId, ...createMessageDto } = parsedValue;
              this.create(createMessageDto, true, createId);
              break;
            case KAFKA_KEYS.GET_ALL:
              this.findAll(true);
              break;
            case KAFKA_KEYS.GET_ONE:
              this.findOne(Number(parsedValue), true);
              break;
            case KAFKA_KEYS.UPDATE:
              this.update(parsedValue, true);
              break;
            case KAFKA_KEYS.DELETE:
              this.remove(Number(parsedValue));
              break;
          }
        },
      }
    );
  }

  async create(
    createMessageDto: CreateMessageDto,
    kafka?: boolean,
    generatedId?: number
  ) {
    const { storyId, content } = createMessageDto;
    let id = this.generateId();

    if (kafka) {
      id = generatedId as number;
    }

    await this.db.execute(
      `INSERT INTO tbl_messages
          (id, "storyId", content) 
        VALUES (?, ?, ?)`,
      [id.toString(), storyId.toString(), content]
    );

    return await this.findOne(+id);
  }

  async findAll(kafka?: boolean) {
    const rows = (await this.db.execute(`SELECT * FROM tbl_messages`)).rows;
    if (kafka) {
      await this.producerService.produce(
        this.createKafkaMessage(this.transformResult(rows))
      );
    }
    return this.transformResult(rows);
  }

  async findOne(id: number, kafka?: boolean) {
    const rows = this.transformResult(
      (
        await this.db.execute(`SELECT * FROM tbl_messages WHERE id = ?`, [
          id.toString(),
        ])
      ).rows
    );

    if (!rows.length) {
      if (kafka) {
        await this.producerService.produce(
          this.createKafkaMessage({
            error: { message: "Message not found", code: HttpStatus.NOT_FOUND },
          })
        );
        return;
      } else {
        throw new HttpException("Message not found", HttpStatus.NOT_FOUND);
      }
    }

    if (kafka) {
      await this.producerService.produce(this.createKafkaMessage(rows[0]));
    }

    return rows[0];
  }

  async update(updateMessageDto: UpdateMessageDto, kafka?: boolean) {
    const { id, storyId, content } = updateMessageDto;

    await this.findOne(id);

    await this.db.execute(
      `UPDATE tbl_messages
        SET "storyId" = ?, "content" = ?
        WHERE "id" = ?`,
      [storyId.toString(), content, id.toString()]
    );

    const result = await this.findOne(id);

    if (kafka) {
      await this.producerService.produce(this.createKafkaMessage(result));
    }

    return result;
  }

  async remove(id: number) {
    await this.findOne(id);

    await this.db.execute(`DELETE FROM tbl_messages WHERE "id" = ?`, [
      id.toString(),
    ]);
  }

  private generateId = (): number => {
    return Math.round(Math.random() * 100000);
  };

  private transformResult = (rows: any) => {
    return rows.map((row) => {
      return {
        id: parseInt(row.id),
        content: row.content,
        storyId: parseInt(row.storyId),
      };
    });
  };

  private createKafkaMessage(value: any) {
    return {
      topic: TOPICS.OUT_TOPIC,
      messages: [{ value: JSON.stringify(value) }],
    };
  }
}
