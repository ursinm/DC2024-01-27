import { Inject, Injectable } from "@nestjs/common";
import { CreateMessageDto } from "./dto/create-Message.dto";
import { UpdateMessageDto } from "./dto/update-Message.dto";
import { PROVIDERS } from "src/constants";

@Injectable()
export class MessagesService {
  constructor(@Inject(PROVIDERS.CASSANDRA) private db: any) {}

  async create(createMessageDto: CreateMessageDto) {
    const { storyId, content } = createMessageDto;
    const id = this.generateId();

    await this.db.execute(
      `INSERT INTO tbl_messages
          (id, "storyId", content) 
        VALUES (?, ?, ?)`,
      [id, storyId.toString(), content]
    );

    return await this.findOne(+id);
  }

  async findAll() {
    const rows = (await this.db.execute(`SELECT * FROM tbl_messages`)).rows;
    return this.transformResult(rows);
  }

  async findOne(id: number) {
    return this.transformResult(
      (
        await this.db.execute(`SELECT * FROM tbl_messages WHERE id = ?`, [
          id.toString(),
        ])
      ).rows
    )[0];
  }

  async update(updateMessageDto: UpdateMessageDto) {
    const { id, storyId, content } = updateMessageDto;

    await this.db.execute(
      `UPDATE tbl_messages
        SET "storyId" = ?, "content" = ?
        WHERE "id" = ?`,
      [storyId.toString(), content, id.toString()]
    );

    return await this.findOne(id);
  }

  async remove(id: number) {
    await this.db.execute(`DELETE FROM tbl_messages WHERE "id" = ?`, [
      id.toString(),
    ]);
  }

  private generateId = () => {
    return Math.round(Math.random() * 100000).toString();
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
}
