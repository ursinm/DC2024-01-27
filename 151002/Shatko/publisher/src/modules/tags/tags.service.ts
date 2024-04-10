import { HttpException, HttpStatus, Inject, Injectable } from '@nestjs/common';
import { CreateTagDto } from './dto/create-tag.dto';
import { UpdateTagDto } from './dto/update-tag.dto';
import { PROVIDERS } from 'src/constants';
import { NodePgDatabase } from 'drizzle-orm/node-postgres';
import { Tag, tags } from 'src/schemas';
import { eq } from 'drizzle-orm';

@Injectable()
export class TagsService {
  constructor(@Inject(PROVIDERS.DRIZZLE) private db: NodePgDatabase) {}

  async create(createTagDto: CreateTagDto): Promise<Tag> {
    if (!(await this.isNameAvailable(createTagDto.name))) {
      throw new HttpException('Name is not available', HttpStatus.FORBIDDEN);
    }

    return (await this.db.insert(tags).values(createTagDto).returning())[0];
  }

  async findAll(): Promise<Tag[]> {
    return await this.db.select().from(tags);
  }

  async findOne(id: number): Promise<Tag> {
    const result = await this.db.select().from(tags).where(eq(tags.id, id));

    if (!result.length) {
      throw new HttpException('Tag not found', HttpStatus.NOT_FOUND);
    }

    return result[0];
  }

  async update(updateTagDto: UpdateTagDto): Promise<Tag> {
    const { id, ...body } = updateTagDto;
    // check if Tag exists
    await this.findOne(id);

    if (!(await this.isNameAvailable(body.name))) {
      throw new HttpException('Name is not available', HttpStatus.FORBIDDEN);
    }

    return (await this.db.update(tags).set(body).where(eq(tags.id, id)).returning())[0];
  }

  async remove(id: number): Promise<void> {
    // check if Tag exists
    await this.findOne(id);

    await this.db.delete(tags).where(eq(tags.id, id));
  }

  async isNameAvailable(name: string): Promise<boolean> {
    const result = await this.db.select().from(tags).where(eq(tags.name, name));
    return !result.length;
  }
}
