import { Entity, PrimaryColumn } from "typeorm";

export class Comment {
    country: string;

    tweetId: number;

    id: number;

    content: string;
}