import { HttpException, Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { CommentRequestToCreate } from '../dto/request/CommentRequestToCreate';
import { CommentRequestToUpdate } from '../dto/request/CommentRequestToUpdate';
import { Comment } from '../entities/Comment';
import { Repository } from 'typeorm';

@Injectable()
export class CommentService {
    constructor(@InjectRepository(Comment) 
        private commentRepositoty: Repository<Comment>){}

    getAll(): Promise<Comment[]>{
        return this.commentRepositoty.find();
    }

    async createComment(commentDto: CommentRequestToCreate): Promise<Comment>{
        let comment = new Comment()
        try {
            comment = await this.commentRepositoty.save(commentDto);
        } catch (error) {
            throw new HttpException(`Tweet с id "${commentDto.tweetId} не существует" `, 403)
        }
        return comment
    }

    getById(id: number): Promise<Comment>{
        return this.commentRepositoty.findOneBy({id});
    }

    async deleteById(id: number): Promise<void>{
        try {
            await this.commentRepositoty.findOneByOrFail({id});
        } catch (error) {
            throw new NotFoundException(`Comment with id: ${id} not found`)
        }
        await this.commentRepositoty.delete(id);
    }

    async updateComment(commentDto: CommentRequestToUpdate): Promise<Comment>{
        const comment = await this.commentRepositoty.findOneBy({id: commentDto.id})
        if (!comment){
            throw new NotFoundException(`Comment with id: ${commentDto.id} not found`)
        }
        comment.content = commentDto.content;
        comment.tweetId = commentDto.tweetId;

        return this.commentRepositoty.save(comment);
    }
}
