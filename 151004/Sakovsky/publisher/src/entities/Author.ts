import { Column, Entity, PrimaryGeneratedColumn } from "typeorm";

@Entity({name: 'tbl_authors'})
export class Author {
    @PrimaryGeneratedColumn()
    id: number;

    @Column({unique: true})
    login: string;

    @Column()
    password: string;

    @Column()
    firstname: string;

    @Column()
    lastname: string;
}