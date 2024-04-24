import { Injectable, NotFoundException } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { Repository } from "typeorm";
import { MarkerRequestToCreate } from "../dto/request/MarkerRequestToCreate";
import { MarkerRequestToUpdate } from "../dto/request/MarkerRequestToUpdate";
import { Marker } from "../entities/Marker";

@Injectable()
export class MarkerService {
    constructor(
        @InjectRepository(Marker) private markerRepositoty: Repository<Marker>,
    ) {}

    getAll(): Promise<Marker[]> {
        return this.markerRepositoty.find();
    }

    createMarker(marker: MarkerRequestToCreate): Promise<Marker> {
        return this.markerRepositoty.save(marker);
    }

    getById(id: number): Promise<Marker> {
        return this.markerRepositoty.findOneBy({ id });
    }

    async deleteById(id: number): Promise<void> {
        try {
            await this.markerRepositoty.findOneByOrFail({ id });
        } catch (error) {
            throw new NotFoundException(`Marker with id: ${id} not found`);
        }
        await this.markerRepositoty.delete(id);
    }

    async updateMarker(markerDto: MarkerRequestToUpdate): Promise<Marker> {
        const marker = await this.markerRepositoty.findOneBy({
            id: markerDto.id,
        });
        if (!marker) {
            throw new NotFoundException(
                `Marker with id: ${markerDto.id} not found.`,
            );
        }
        marker.name = markerDto.name;

        return this.markerRepositoty.save(marker);
    }
}
