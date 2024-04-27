import { Module } from "@nestjs/common";
import { StoriesService } from "./stories.service";
import { StoriesController } from "./stories.controller";
import { DrizzleProvider } from "../drizzle/drizzle.provider";
import { EditorsService } from "../editors/editors.service";

@Module({
  controllers: [StoriesController],
  providers: [StoriesService, DrizzleProvider, EditorsService],
})
export class StoriesModule {}
