import { Body, Controller, Delete, Get, HttpCode, NotFoundException, Param, ParseIntPipe, Post, Put, UsePipes, ValidationPipe } from '@nestjs/common';
import { AuthorRequestToCreate } from '../dto/request/AuthorRequestToCreate';
import { AuthorResponseTo } from '../dto/response/AuthorResponseTo';
import { AuthorService } from './author.service';
import { AuthorRequestToUpdate } from '../dto/request/AuthorRequestToUpdate';

@Controller('authors')
export class AuthorController {
    constructor(private readonly authorService: AuthorService ){}

    @Get()
    async getAll(): Promise<AuthorResponseTo[]>{
        const authorsDto: AuthorResponseTo[]= [];
        const authors = await this.authorService.getAll();
        for (const author of authors) {
            const authorDto = new AuthorResponseTo();
            authorDto.id = author.id;
            authorDto.firstname = author.firstname;
            authorDto.lastname = author.lastname;
            authorDto.login = author.login;
            authorDto.password = author.password;
            authorsDto.push(authorDto);
        }

        return authorsDto;
    }

    @Get(':id')
    async getById(@Param('id', ParseIntPipe) id: number): Promise<AuthorResponseTo>{
        const author = await this.authorService.getById(id);
        if(!author){
            throw new NotFoundException('User not found')
        }
        const authorDto = new AuthorResponseTo();
        authorDto.id = author.id;
        authorDto.firstname = author.firstname;
        authorDto.lastname = author.lastname;
        authorDto.login = author.login;
        authorDto.password = author.password;
        return authorDto;
    }

    @UsePipes(new ValidationPipe())
    @Post()
    async createAuthor(@Body() dto: AuthorRequestToCreate): Promise<AuthorResponseTo>{
        const author = await this.authorService.createAuthor(dto);

        const authorDto = new AuthorResponseTo();
        authorDto.id = author.id;
        authorDto.firstname = author.firstname;
        authorDto.lastname = author.lastname;
        authorDto.login = author.login;
        authorDto.password = author.password;
        return authorDto;
    }

    @UsePipes(new ValidationPipe())
    @Put()
    async updateAuthor( @Body() authorReqDto: AuthorRequestToUpdate): Promise<AuthorResponseTo>{
        const author = await this.authorService.updateAuthor(authorReqDto);
        const authorDto = new AuthorResponseTo();
        authorDto.id = author.id;
        authorDto.firstname = author.firstname;
        authorDto.lastname = author.lastname;
        authorDto.login = author.login;
        authorDto.password = author.password;
        return authorDto;

    }

    @HttpCode(204)
    @Delete(':id')
    deleteAuthor(@Param('id', ParseIntPipe) id: number): Promise<void>{
        return this.authorService.deleteById(id);
    }
}
