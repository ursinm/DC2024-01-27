import { Body, Controller, Delete, Get, HttpCode, NotFoundException, Param, ParseIntPipe, Post, Put, UsePipes, ValidationPipe } from '@nestjs/common';
import { MarkerRequestToCreate } from '../dto/request/MarkerRequestToCreate';
import { MarkerResponseTo } from '../dto/response/MarkerResponseTo';
import { MarkerService } from './marker.service';
import { MarkerRequestToUpdate } from '../dto/request/MarkerRequestToUpdate';

@Controller('markers')
export class MarkerController {
    constructor(private readonly markerService: MarkerService ){}

    @Get()
    async getAll(): Promise<MarkerResponseTo[]>{
        const result: MarkerResponseTo[] = [];
        const markers = await this.markerService.getAll();
        for (const marker of markers) {
            const markerDto = new MarkerResponseTo();
            markerDto.id = marker.id;
            markerDto.name = marker.name;
            result.push(markerDto);
        }
        return result;
    }

    @Get(':id')
    async getById(@Param('id', ParseIntPipe) id: number): Promise<MarkerResponseTo>{
        const markerDto = new MarkerResponseTo();
        const marker = await this.markerService.getById(id);
        if(!marker){
            throw new NotFoundException('Marker not found');
        }
        markerDto.name = marker.name;
        markerDto.id = marker.id;
        return markerDto;
    }

    @UsePipes(new ValidationPipe())
    @Post()
    async createMarker(@Body() dto: MarkerRequestToCreate): Promise<MarkerResponseTo>{
        const markerDto = new MarkerResponseTo();
        const marker =  await this.markerService.createMarker(dto);
        markerDto.name = marker.name;
        markerDto.id = marker.id;
        return markerDto;
    }

    @UsePipes(new ValidationPipe())
    @Put()
    async updateMarker(@Body() authorDto: MarkerRequestToUpdate): Promise<MarkerResponseTo>{
        const markerDto = new MarkerResponseTo();
        const marker =  await this.markerService.updateMarker(authorDto);
        markerDto.name = marker.name;
        markerDto.id = marker.id;
        return markerDto;
    }

    @HttpCode(204)
    @Delete(':id')
    deleteMarker(@Param('id', ParseIntPipe) id: number): Promise<void>{
        return this.markerService.deleteById(id);
    }
}
