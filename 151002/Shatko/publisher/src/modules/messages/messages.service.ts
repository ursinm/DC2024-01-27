import { HttpException, HttpStatus, Inject, Injectable } from "@nestjs/common";
import { CreateMessageDto } from "./dto/create-Message.dto";
import { UpdateMessageDto } from "./dto/update-Message.dto";
import { PROVIDERS } from "src/constants";
import { NodePgDatabase } from "drizzle-orm/node-postgres";
import { Message } from "src/schemas";
import { StoriesService } from "../stories/stories.service";
import axios from "axios";

@Injectable()
export class MessagesService {
  constructor(
    @Inject(PROVIDERS.DRIZZLE) private db: NodePgDatabase,
    private storiesService: StoriesService
  ) {}

  async create(createMessageDto: CreateMessageDto): Promise<Message> {
    // check if story exists
    await this.storiesService.findOne(createMessageDto.storyId);

    const result = await axios.post(
      `http://localhost:${process.env.DISCUSSION_PORT}/api/v1.0/messages`,
      createMessageDto
    );

    return result.data;
  }

  async findAll(): Promise<Message[]> {
    const result = await axios.get(
      `http://localhost:${process.env.DISCUSSION_PORT}/api/v1.0/messages`
    );
    return result.data;
  }

  async findOne(id: number): Promise<Message> {
    const result = await axios.get(
      `http://localhost:${process.env.DISCUSSION_PORT}/api/v1.0/messages/${id}`
    );

    if (!result.data) {
      throw new HttpException("Message not found", HttpStatus.NOT_FOUND);
    }

    return result.data;
  }

  async update(updateMessageDto: UpdateMessageDto): Promise<Message> {
    // check if message exists
    await this.findOne(updateMessageDto.id);

    // check if story exists
    await this.storiesService.findOne(updateMessageDto.storyId);

    const result = await axios.put(
      `http://localhost:${process.env.DISCUSSION_PORT}/api/v1.0/messages`,
      updateMessageDto
    );

    return result.data;
  }

  async remove(id: number): Promise<void> {
    // check if message exists
    await this.findOne(id);

    await axios.delete(
      `http://localhost:${process.env.DISCUSSION_PORT}/api/v1.0/messages/${id}`
    );
  }
}
