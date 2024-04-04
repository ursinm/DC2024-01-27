import { TweetRequestToCreate } from "src/dto/request/TweetRequestToCreate";
import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from "typeorm";
import { Author } from "./Author";

@Entity({name: 'tbl_tweets'})
export class Tweet {
    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    @ManyToOne(() => Author, (author) => author.id)
    authorId: number;

    @Column({unique: true})
    title: string;

    @Column()
    content: string;

    @Column({nullable: false, default: new Date().toISOString().slice(0, 19).replace("T", " ")})
    created: Date;

    @Column({nullable: true, default: null})
    modified: Date;

    setTweet(dto: TweetRequestToCreate){
        this.authorId = dto.authorId;
        this.content = dto.content;
        this.title = dto.title;
        this.created = new Date();//new Date().toISOString().slice(0, 19).replace("T", " ")
        this.modified = null;
    }
}