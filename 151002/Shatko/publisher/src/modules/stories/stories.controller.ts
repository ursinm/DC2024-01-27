import {
  Controller,
  Get,
  Post,
  Body,
  Patch,
  Param,
  Delete,
  Put,
  HttpCode,
  ParseIntPipe,
  UsePipes,
} from "@nestjs/common";
import { StoriesService } from "./stories.service";
import { CreateStoryDto } from "./dto/create-story.dto";
import { UpdateStoryDto } from "./dto/update-story.dto";
import { ZodValidationPipe } from "nestjs-zod";

// required typo
@Controller("storys")
@UsePipes(ZodValidationPipe)
export class StoriesController {
  constructor(private readonly storiesService: StoriesService) {}

  @Post()
  create(@Body() createStoryDto: CreateStoryDto) {
    return this.storiesService.create(createStoryDto);
  }

  @Get()
  findAll() {
    return this.storiesService.findAll();
  }

  @Get(":id")
  findOne(@Param("id", ParseIntPipe) id: number) {
    return this.storiesService.findOne(id);
  }

  @Put()
  update(@Body() updateStoryDto: UpdateStoryDto) {
    return this.storiesService.update(updateStoryDto);
  }

  @Delete(":id")
  @HttpCode(204)
  remove(@Param("id", ParseIntPipe) id: number) {
    return this.storiesService.remove(id);
  }
}
