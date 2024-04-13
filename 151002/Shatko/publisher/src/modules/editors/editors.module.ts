import { Module } from "@nestjs/common";
import { EditorsService } from "./editors.service";
import { EditorsController } from "./editors.controller";
import { DrizzleProvider } from "../drizzle/drizzle.provider";

@Module({
  controllers: [EditorsController],
  providers: [EditorsService, DrizzleProvider],
})
export class EditorsModule {}
