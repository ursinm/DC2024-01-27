import { HttpException, HttpStatus, Inject, Injectable } from '@nestjs/common';
import { CreateStoryDto } from './dto/create-story.dto';
import { UpdateStoryDto } from './dto/update-story.dto';
import { PROVIDERS } from 'src/constants';
import { NodePgDatabase } from 'drizzle-orm/node-postgres';
import { Story, stories } from 'src/schemas';
import { eq } from 'drizzle-orm';
import { EditorsService } from '../editors/editors.service';

@Injectable()
export class StoriesService {
  constructor(
    @Inject(PROVIDERS.DRIZZLE) private db: NodePgDatabase,
    private editorsService: EditorsService,
  ) {}

  async create(createStoryDto: CreateStoryDto): Promise<Story> {
    // check if editor exists
    await this.editorsService.findOne(createStoryDto.editorId);

    if (!(await this.isTitleAvailable(createStoryDto.title))) {
      throw new HttpException('Title is not available', HttpStatus.FORBIDDEN);
    }

    return (await this.db.insert(stories).values(createStoryDto).returning())[0];
  }

  async findAll(): Promise<Story[]> {
    return await this.db.select().from(stories);
  }

  async findOne(id: number): Promise<Story> {
    const result = await this.db.select().from(stories).where(eq(stories.id, id));

    if (!result.length) {
      throw new HttpException('Story not found', HttpStatus.NOT_FOUND);
    }

    return result[0];
  }

  async update(updateStoryDto: UpdateStoryDto): Promise<Story> {
    const { id, ...body } = updateStoryDto;
    // check if story exists
    await this.findOne(id);

    // check if editor exists
    await this.editorsService.findOne(updateStoryDto.editorId);

    if (!(await this.isTitleAvailable(body.title))) {
      throw new HttpException('Title is not available', HttpStatus.FORBIDDEN);
    }

    return (
      await this.db
        .update(stories)
        .set({ ...body, modified: new Date() })
        .where(eq(stories.id, id))
        .returning()
    )[0];
  }

  async remove(id: number): Promise<void> {
    // check if story exists
    await this.findOne(id);

    await this.db.delete(stories).where(eq(stories.id, id));
  }

  async isTitleAvailable(title: string): Promise<boolean> {
    const result = await this.db.select().from(stories).where(eq(stories.title, title));
    return !result.length;
  }
}
