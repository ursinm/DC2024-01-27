import { HttpException, HttpStatus, Inject, Injectable } from "@nestjs/common";
import { CreateEditorDto } from "./dto/create-editor.dto";
import { UpdateEditorDto } from "./dto/update-editor.dto";
import { PROVIDERS, REDIS_KEYS } from "src/constants";
import { NodePgDatabase } from "drizzle-orm/node-postgres";
import { Editor, editors } from "src/schemas/editors.schema";
import { eq } from "drizzle-orm";
import { CACHE_MANAGER } from "@nestjs/cache-manager";
import { Cache } from "cache-manager";

@Injectable()
export class EditorsService {
  constructor(
    @Inject(PROVIDERS.DRIZZLE) private db: NodePgDatabase,
    @Inject(CACHE_MANAGER) private cacheManager: Cache
  ) {}

  async create(createEditorDto: CreateEditorDto): Promise<Editor> {
    if (!(await this.isLoginAvailable(createEditorDto.login))) {
      throw new HttpException("Login is not available", HttpStatus.FORBIDDEN);
    }

    await this.cacheManager.del(REDIS_KEYS.EDITORS_GET_ALL);

    return (
      await this.db.insert(editors).values(createEditorDto).returning()
    )[0];
  }

  async findAll(): Promise<Editor[]> {
    const cache = await this.cacheManager.get<Editor[]>(
      REDIS_KEYS.EDITORS_GET_ALL
    );
    if (cache) {
      return cache;
    }

    const result = await this.db.select().from(editors);

    await this.cacheManager.set(REDIS_KEYS.EDITORS_GET_ALL, result);

    return result;
  }

  async findOne(id: number): Promise<Editor> {
    const cache = await this.cacheManager.get<Editor>(
      this.getEntityCacheKey(id)
    );
    if (cache) {
      return cache;
    }

    const result = await this.db
      .select()
      .from(editors)
      .where(eq(editors.id, id));

    if (!result.length) {
      throw new HttpException("Editor not found", HttpStatus.NOT_FOUND);
    }

    await this.cacheManager.set(this.getEntityCacheKey(id), result[0]);

    return result[0];
  }

  async update(updateEditorDto: UpdateEditorDto): Promise<Editor> {
    const { id, ...body } = updateEditorDto;
    // check if editor exists
    await this.findOne(id);

    if (!(await this.isLoginAvailable(body.login))) {
      throw new HttpException("Login is not available", HttpStatus.FORBIDDEN);
    }

    await this.cacheManager.del(REDIS_KEYS.EDITORS_GET_ALL);
    await this.cacheManager.del(this.getEntityCacheKey(id));

    return (
      await this.db
        .update(editors)
        .set(body)
        .where(eq(editors.id, id))
        .returning()
    )[0];
  }

  async remove(id: number): Promise<void> {
    // check if editor exists
    await this.findOne(id);

    await this.cacheManager.del(REDIS_KEYS.EDITORS_GET_ALL);
    await this.cacheManager.del(this.getEntityCacheKey(id));

    await this.db.delete(editors).where(eq(editors.id, id));
  }

  async isLoginAvailable(login: string): Promise<boolean> {
    const result = await this.db
      .select()
      .from(editors)
      .where(eq(editors.login, login));
    return !result.length;
  }

  getEntityCacheKey(id: number) {
    return `${REDIS_KEYS.EDITORS_GET_ONE}.${id}`;
  }
}
