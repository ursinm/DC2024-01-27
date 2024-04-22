import {
  Body,
  Controller,
  Delete,
  Get,
  HttpCode,
  NotFoundException,
  Param,
  ParseIntPipe,
  Post,
  Put,
  UsePipes,
  ValidationPipe,
} from '@nestjs/common';
import { CommentRequestToCreate } from '../dto/request/CommentRequestToCreate';
import { CommentRequestToUpdate } from '../dto/request/CommentRequestToUpdate';
import { CommentResponseTo } from '../dto/response/CommentResponseTo';
import { CommentService } from './comment.service';

@Controller('comments')
export class CommentController {
  constructor(private readonly commentService: CommentService) {}

  @Get()
  async getAll(): Promise<CommentResponseTo[]> {
    const commentsDto: CommentResponseTo[] = [];
    const comments = await this.commentService.getAll();
    for (const comment of comments) {
      const commetnDto = new CommentResponseTo();
      commetnDto.content = comment.content;
      commetnDto.id = comment.id;
      commetnDto.tweetId = comment.tweetId;
      commentsDto.push(commetnDto);
    }

    return commentsDto;
  }

  @Get(':id')
  async getById(
    @Param('id', ParseIntPipe) id: number,
  ): Promise<CommentResponseTo> {
    const comment = await this.commentService.getById(id);
    if (!comment) {
      throw new NotFoundException('Comment not found');
    }
    const commentDto = new CommentResponseTo();
    commentDto.content = comment.content;
    commentDto.id = comment.id;
    commentDto.tweetId = comment.tweetId;
    return commentDto;
  }

  @UsePipes(new ValidationPipe())
  @Post()
  async createComent(
    @Body() commentReqDto: CommentRequestToCreate,
  ): Promise<CommentResponseTo> {
    const comment = await this.commentService.createComment(commentReqDto);
    const commentRespDto = new CommentResponseTo();
    commentRespDto.content = comment.content;
    commentRespDto.id = comment.id;
    commentRespDto.tweetId = comment.tweetId;
    return commentRespDto;
  }

  @UsePipes(new ValidationPipe())
  @Put()
  async updateComment(
    @Body() commentReqDto: CommentRequestToUpdate,
  ): Promise<CommentResponseTo> {
    const comment = await this.commentService.updateComment(commentReqDto);
    const commentRespDto = new CommentResponseTo();
    commentRespDto.content = comment.content;
    commentRespDto.id = comment.id;
    commentRespDto.tweetId = comment.tweetId;
    return commentRespDto;
  }

  @HttpCode(204)
  @Delete(':id')
  deleteComment(@Param('id', ParseIntPipe) id: number): Promise<void> {
    return this.commentService.deleteById(id);
  }
}
