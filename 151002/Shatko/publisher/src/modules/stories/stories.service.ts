import { HttpException, HttpStatus, Inject, Injectable } from "@nestjs/common";
import { CreateStoryDto } from "./dto/create-story.dto";
import { UpdateStoryDto } from "./dto/update-story.dto";
import { PROVIDERS, REDIS_KEYS } from "src/constants";
import { NodePgDatabase } from "drizzle-orm/node-postgres";
import { Story, stories } from "src/schemas";
import { eq } from "drizzle-orm";
import { EditorsService } from "../editors/editors.service";
import { CACHE_MANAGER } from "@nestjs/cache-manager";
import { Cache } from "cache-manager";

@Injectable()
export class StoriesService {
  constructor(
    @Inject(PROVIDERS.DRIZZLE) private db: NodePgDatabase,
    private editorsService: EditorsService,
    @Inject(CACHE_MANAGER) private cacheManager: Cache
  ) {}

  async create(createStoryDto: CreateStoryDto): Promise<Story> {
    // check if editor exists
    await this.editorsService.findOne(createStoryDto.editorId);

    if (!(await this.isTitleAvailable(createStoryDto.title))) {
      throw new HttpException("Title is not available", HttpStatus.FORBIDDEN);
    }

    await this.cacheManager.del(REDIS_KEYS.STORIES_GET_ALL);

    return (
      await this.db.insert(stories).values(createStoryDto).returning()
    )[0];
  }

  async findAll(): Promise<Story[]> {
    const cache = await this.cacheManager.get<Story[]>(
      REDIS_KEYS.STORIES_GET_ALL
    );
    if (cache) {
      return cache;
    }

    const result = await this.db.select().from(stories);

    await this.cacheManager.set(REDIS_KEYS.STORIES_GET_ALL, result);

    return result;
  }

  async findOne(id: number): Promise<Story> {
    const cache = await this.cacheManager.get<Story>(
      this.getEntityCacheKey(id)
    );
    if (cache) {
      return cache;
    }

    const result = await this.db
      .select()
      .from(stories)
      .where(eq(stories.id, id));

    if (!result.length) {
      throw new HttpException("Story not found", HttpStatus.NOT_FOUND);
    }

    await this.cacheManager.set(this.getEntityCacheKey(id), result[0]);

    return result[0];
  }

  async update(updateStoryDto: UpdateStoryDto): Promise<Story> {
    const { id, ...body } = updateStoryDto;
    // check if story exists
    await this.findOne(id);

    // check if editor exists
    await this.editorsService.findOne(updateStoryDto.editorId);

    if (!(await this.isTitleAvailable(body.title))) {
      throw new HttpException("Title is not available", HttpStatus.FORBIDDEN);
    }

    await this.cacheManager.del(REDIS_KEYS.STORIES_GET_ALL);
    await this.cacheManager.del(this.getEntityCacheKey(id));

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

    await this.cacheManager.del(REDIS_KEYS.STORIES_GET_ALL);
    await this.cacheManager.del(this.getEntityCacheKey(id));

    await this.db.delete(stories).where(eq(stories.id, id));
  }

  async isTitleAvailable(title: string): Promise<boolean> {
    const result = await this.db
      .select()
      .from(stories)
      .where(eq(stories.title, title));
    return !result.length;
  }

  getEntityCacheKey(id: number) {
    return `${REDIS_KEYS.STORIES_GET_ONE}.${id}`;
  }
}
