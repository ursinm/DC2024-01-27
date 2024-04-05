import { HttpException, HttpStatus, Inject, Injectable } from '@nestjs/common';
import { CreateMessageDto } from './dto/create-Message.dto';
import { UpdateMessageDto } from './dto/update-Message.dto';
import { PROVIDERS } from 'src/constants';
import { NodePgDatabase } from 'drizzle-orm/node-postgres';
import { Message, messages } from 'src/schemas';
import { StoriesService } from '../stories/stories.service';
import { eq } from 'drizzle-orm';

@Injectable()
export class MessagesService {
  constructor(
    @Inject(PROVIDERS.DRIZZLE) private db: NodePgDatabase,
    private storiesService: StoriesService,
  ) {}

  async create(createMessageDto: CreateMessageDto): Promise<Message> {
    // check if story exists
    await this.storiesService.findOne(createMessageDto.storyId);

    return (await this.db.insert(messages).values(createMessageDto).returning())[0];
  }

  async findAll(): Promise<Message[]> {
    return await this.db.select().from(messages);
  }

  async findOne(id: number): Promise<Message> {
    const result = await this.db.select().from(messages).where(eq(messages.id, id));

    if (!result.length) {
      throw new HttpException('Message not found', HttpStatus.NOT_FOUND);
    }

    return result[0];
  }

  async update(updateMessageDto: UpdateMessageDto): Promise<Message> {
    const { id, ...body } = updateMessageDto;
    // check if message exists
    await this.findOne(id);

    // check if story exists
    await this.storiesService.findOne(updateMessageDto.storyId);

    return (await this.db.update(messages).set(body).where(eq(messages.id, id)).returning())[0];
  }

  async remove(id: number): Promise<void> {
    // check if message exists
    await this.findOne(id);

    await this.db.delete(messages).where(eq(messages.id, id));
  }
}
