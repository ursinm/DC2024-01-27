import {
  Controller,
  Get,
  Post,
  Body,
  Param,
  Delete,
  UsePipes,
  HttpCode,
  Put,
  ParseIntPipe,
} from "@nestjs/common";
import { EditorsService } from "./editors.service";
import { CreateEditorDto } from "./dto/create-editor.dto";
import { UpdateEditorDto } from "./dto/update-editor.dto";
import { ZodValidationPipe } from "nestjs-zod";
import { CacheKey } from "@nestjs/cache-manager";
import { REDIS_KEYS } from "src/constants";

@Controller("editors")
@UsePipes(ZodValidationPipe)
export class EditorsController {
  constructor(private readonly editorsService: EditorsService) {}

  @Post()
  create(@Body() createEditorDto: CreateEditorDto) {
    return this.editorsService.create(createEditorDto);
  }

  @Get()
  findAll() {
    return this.editorsService.findAll();
  }

  @Get(":id")
  findOne(@Param("id", ParseIntPipe) id: number) {
    return this.editorsService.findOne(id);
  }

  @Put()
  update(@Body() updateEditorDto: UpdateEditorDto) {
    return this.editorsService.update(updateEditorDto);
  }

  @Delete(":id")
  @HttpCode(204)
  remove(@Param("id", ParseIntPipe) id: number) {
    return this.editorsService.remove(id);
  }
}
