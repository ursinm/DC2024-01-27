import { HttpException, HttpStatus, Inject, Injectable } from '@nestjs/common';
import { CreateEditorDto } from './dto/create-editor.dto';
import { UpdateEditorDto } from './dto/update-editor.dto';
import { PROVIDERS } from 'src/constants';
import { NodePgDatabase } from 'drizzle-orm/node-postgres';
import { Editor, editors } from 'src/schemas/editors.schema';
import { eq } from 'drizzle-orm';

@Injectable()
export class EditorsService {
  constructor(@Inject(PROVIDERS.DRIZZLE) private db: NodePgDatabase) {}

  async create(createEditorDto: CreateEditorDto): Promise<Editor> {
    if (!(await this.isLoginAvailable(createEditorDto.login))) {
      throw new HttpException('Login is not available', HttpStatus.FORBIDDEN);
    }

    return (await this.db.insert(editors).values(createEditorDto).returning())[0];
  }

  async findAll(): Promise<Editor[]> {
    return await this.db.select().from(editors);
  }

  async findOne(id: number): Promise<Editor> {
    const result = await this.db.select().from(editors).where(eq(editors.id, id));

    if (!result.length) {
      throw new HttpException('Editor not found', HttpStatus.NOT_FOUND);
    }

    return result[0];
  }

  async update(updateEditorDto: UpdateEditorDto): Promise<Editor> {
    const { id, ...body } = updateEditorDto;
    // check if editor exists
    await this.findOne(id);

    if (!(await this.isLoginAvailable(body.login))) {
      throw new HttpException('Login is not available', HttpStatus.FORBIDDEN);
    }

    return (await this.db.update(editors).set(body).where(eq(editors.id, id)).returning())[0];
  }

  async remove(id: number): Promise<void> {
    // check if editor exists
    await this.findOne(id);

    await this.db.delete(editors).where(eq(editors.id, id));
  }

  async isLoginAvailable(login: string): Promise<boolean> {
    const result = await this.db.select().from(editors).where(eq(editors.login, login));
    return !result.length;
  }
}
