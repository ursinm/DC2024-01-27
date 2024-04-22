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
import { Comment } from 'src/entites/Comment/Comment';
import { CommentService } from './comment.service';
import { CreateCommentDto } from './dto/CreateCommentDto';
import { UpdateCommentDto } from './dto/UpdateCommentDto';

@Controller('comments')
export class CommentController {
  constructor(private readonly commentService: CommentService) {}

  @Get()
  async getAll(): Promise<Comment[]> {
    const commentsDto: Comment[] = [];
    const comments = await this.commentService.getAll();
    for (const comment of comments) {
      const commetnDto = new Comment();
      commetnDto.content = comment.content;
      commetnDto.id = comment.id;
      commetnDto.tweetId = comment.tweetId;
      commentsDto.push(commetnDto);
    }

    return commentsDto;
  }

  @Get(':id')
  async getById(@Param('id', ParseIntPipe) id: number): Promise<Comment> {
    const comment = await this.commentService.getById(id);
    if (!comment) {
      throw new NotFoundException('Comment not found');
    }
    const commentDto = new Comment();
    commentDto.content = comment.content;
    commentDto.id = comment.id;
    commentDto.tweetId = comment.tweetId;
    return commentDto;
  }

  @UsePipes(new ValidationPipe())
  @Post()
  async createComent(
    @Body() commentReqDto: CreateCommentDto,
  ): Promise<Comment> {
    const comment = await this.commentService.createComment(commentReqDto);
    const commentRespDto = new Comment();
    commentRespDto.content = comment.content;
    commentRespDto.id = comment.id;
    commentRespDto.tweetId = comment.tweetId;
    return commentRespDto;
  }

  @UsePipes(new ValidationPipe())
  @Put()
  async updateComment(
    @Body() commentReqDto: UpdateCommentDto,
  ): Promise<Comment> {
    const comment = await this.commentService.updateComment(commentReqDto);
    const commentRespDto = new Comment();
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
