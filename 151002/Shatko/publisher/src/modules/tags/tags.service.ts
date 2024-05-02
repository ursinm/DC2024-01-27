import { HttpException, HttpStatus, Inject, Injectable } from "@nestjs/common";
import { CreateTagDto } from "./dto/create-tag.dto";
import { UpdateTagDto } from "./dto/update-tag.dto";
import { PROVIDERS, REDIS_KEYS } from "src/constants";
import { NodePgDatabase } from "drizzle-orm/node-postgres";
import { Tag, tags } from "src/schemas";
import { eq } from "drizzle-orm";
import { CACHE_MANAGER } from "@nestjs/cache-manager";
import { Cache } from "cache-manager";

@Injectable()
export class TagsService {
  constructor(
    @Inject(PROVIDERS.DRIZZLE) private db: NodePgDatabase,
    @Inject(CACHE_MANAGER) private cacheManager: Cache
  ) {}

  async create(createTagDto: CreateTagDto): Promise<Tag> {
    if (!(await this.isNameAvailable(createTagDto.name))) {
      throw new HttpException("Name is not available", HttpStatus.FORBIDDEN);
    }

    await this.cacheManager.del(REDIS_KEYS.TAGS_GET_ALL);

    return (await this.db.insert(tags).values(createTagDto).returning())[0];
  }

  async findAll(): Promise<Tag[]> {
    const cache = await this.cacheManager.get<Tag[]>(REDIS_KEYS.TAGS_GET_ALL);
    if (cache) {
      return cache;
    }

    const result = await this.db.select().from(tags);

    await this.cacheManager.set(REDIS_KEYS.TAGS_GET_ALL, result);

    return result;
  }

  async findOne(id: number): Promise<Tag> {
    const cache = await this.cacheManager.get<Tag>(this.getEntityCacheKey(id));
    if (cache) {
      return cache;
    }

    const result = await this.db.select().from(tags).where(eq(tags.id, id));

    if (!result.length) {
      throw new HttpException("Editor not found", HttpStatus.NOT_FOUND);
    }

    await this.cacheManager.set(this.getEntityCacheKey(id), result[0]);

    return result[0];
  }

  async update(updateTagDto: UpdateTagDto): Promise<Tag> {
    const { id, ...body } = updateTagDto;
    // check if Tag exists
    await this.findOne(id);

    if (!(await this.isNameAvailable(body.name))) {
      throw new HttpException("Name is not available", HttpStatus.FORBIDDEN);
    }

    await this.cacheManager.del(REDIS_KEYS.TAGS_GET_ALL);
    await this.cacheManager.del(this.getEntityCacheKey(id));

    return (
      await this.db.update(tags).set(body).where(eq(tags.id, id)).returning()
    )[0];
  }

  async remove(id: number): Promise<void> {
    // check if Tag exists
    await this.findOne(id);

    await this.cacheManager.del(REDIS_KEYS.TAGS_GET_ALL);
    await this.cacheManager.del(this.getEntityCacheKey(id));

    await this.db.delete(tags).where(eq(tags.id, id));
  }

  async isNameAvailable(name: string): Promise<boolean> {
    const result = await this.db.select().from(tags).where(eq(tags.name, name));
    return !result.length;
  }

  getEntityCacheKey(id: number) {
    return `${REDIS_KEYS.TAGS_GET_ONE}.${id}`;
  }
}
