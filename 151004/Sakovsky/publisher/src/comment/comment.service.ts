import {
    HttpException,
    Injectable,
    NotFoundException
} from "@nestjs/common";
import { Client, ClientKafka, Transport } from "@nestjs/microservices";
import { firstValueFrom } from "rxjs";
import { ADD_NEW_COMMENT, DELETE_COMMENT_BY_ID, GET_COMMENT_BY_ID, GET_COMMENT_LIST, UPDATE_COMMENT } from "src/constants/constants";
import { CommentRequestToCreate } from "../dto/request/CommentRequestToCreate";
import { CommentRequestToUpdate } from "../dto/request/CommentRequestToUpdate";
import { Comment } from "../entities/Comment";

@Injectable()
export class CommentService {

    @Client({
        transport: Transport.KAFKA,
        options: {
            client: {
                clientId: "comment",
                brokers: ["localhost:9092"],
            },
            consumer: {
                groupId: "comment-consumer",
            },
        },
    })
    private client: ClientKafka;

    async onModuleInit() {
        this.client.subscribeToResponseOf(ADD_NEW_COMMENT);
        this.client.subscribeToResponseOf(GET_COMMENT_LIST);
        this.client.subscribeToResponseOf(GET_COMMENT_BY_ID);
        this.client.subscribeToResponseOf(UPDATE_COMMENT);
        this.client.subscribeToResponseOf(DELETE_COMMENT_BY_ID);

        await this.client.connect();
    }

    async getAll() {
        const response = this.client.send<Comment[], any>(
            "get.comment.list",
            "",
        );
        return response;
    }

    async createComment(commentDto: CommentRequestToCreate) {
        let comment = new Comment();
        try {
            commentDto.id = Math.floor(Math.random() * (10000 - 100 + 1)) + 100;
            comment = await firstValueFrom(
                this.client.send<Comment, CommentRequestToCreate>(
                    "add.new.comment",
                    commentDto,
                ),
            );
        } catch (error) {
            throw new HttpException(
                `Tweet с id "${commentDto.tweetId} уже существует" `,
                403,
            );
        }
        return comment;
    }

    async getById(id: number) {
        try {
            return await firstValueFrom(
                this.client.send<Comment>("get.comment.byId", {
                    commentId: id,
                }),
            );
        } catch (error) {
            throw new HttpException(`Tweet с id "${id} не существует" `, 403);
        }
    }

    async deleteById(id: number) {
        try {
            const response = await firstValueFrom(
                this.client.send<boolean, any>("delete.comment.byId", {
                    commentId: id,
                }),
            );
            return response;
        } catch (error) {
            throw new NotFoundException(`Comment with id: ${id} not found`);
        }
    }

    async updateComment(commentDto: CommentRequestToUpdate): Promise<Comment> {
        try {
            return await firstValueFrom(
                this.client.send<Comment, CommentRequestToUpdate>(
                    "update.comment",
                    commentDto,
                ),
            );
        } catch (error) {
            throw new NotFoundException(
                `Comment with id: ${commentDto.id} not found`,
            );
        }
    }
}
