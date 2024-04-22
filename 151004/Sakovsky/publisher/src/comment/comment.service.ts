import { HttpException, Injectable, NotFoundException, } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { CommentRequestToCreate } from '../dto/request/CommentRequestToCreate';
import { CommentRequestToUpdate } from '../dto/request/CommentRequestToUpdate';
import { Comment } from '../entities/Comment';
import { Repository } from 'typeorm';
import axios from 'axios';

const DISCUSSION_URL = 'http://localhost:24130/api/v1.0/comments'

@Injectable()
export class CommentService {
    async getAll(): Promise<Comment[]>{
        const response = axios.get<Comment[]>(DISCUSSION_URL);
        return (await response).data;
    }

    async createComment(commentDto: CommentRequestToCreate): Promise<Comment>{
        let comment = new Comment()
        try {
            commentDto.id = Math.floor(Math.random() * (10000 - 100 + 1)) + 100;
            comment = (await axios.post<Comment>(DISCUSSION_URL,commentDto )).data;
        } catch (error) {
            throw new HttpException(`Tweet с id "${commentDto.tweetId} уже существует" `, 403)
        }
        return comment
    }

    async getById(id: number): Promise<Comment>{
        let comment = new Comment()
        try {
            comment = (await axios.get<Comment>(DISCUSSION_URL+`/${id}`,)).data;
        } catch (error) {
            throw new HttpException(`Tweet с id "${id} не существует" `, 403);
        }
        return comment;
    }

    async deleteById(id: number): Promise<void>{
        try {
            await axios.delete(DISCUSSION_URL+`/${id}`);
        } catch (error) {
            throw new NotFoundException(`Comment with id: ${id} not found`)
        }
    }

    async updateComment(commentDto: CommentRequestToUpdate): Promise<Comment>{
        let comment = new Comment();
        try {
            comment = (await axios.put<Comment>(DISCUSSION_URL, commentDto)).data;
        } catch (error) {
            throw new NotFoundException(`Comment with id: ${commentDto.id} not found`);
        }
        return comment;
    }
}
