using Lab1.DTO;
using Lab1.Services.Interfaces;
using Lab1.Models;
using AutoMapper;
using Microsoft.EntityFrameworkCore;
using Lab1.DTO.Interface;
using Microsoft.Data.Sqlite;
using Lab1.DB.BaseDBContext;

namespace Lab1.Services
{
    public class CreatorService(IMapper _mapper, BaseContext dbContext) : ICreatorService
    {
        public async Task<IResponseTo> CreateEnt(IRequestTo Dto)
        {
            var CreatorDto = (CreatorRequestTo)Dto;

            if (!Validate(CreatorDto))
            {
                throw new InvalidDataException("Incorrect data for CREATE Creator");

            }
            var Creator = _mapper.Map<Creator>(CreatorDto);
            dbContext.Add(Creator);
            await dbContext.SaveChangesAsync();
            var response = _mapper.Map<CreatorResponseTo>(Creator);
            return response;




        }

        public async Task DeleteEnt(int id)
        {
            try
            {
                var Creator = await dbContext.Creators.FindAsync(id);
                dbContext.Creators.Remove(Creator!);
                await dbContext.SaveChangesAsync();
                return;
            }
            catch
            {
                throw new Exception("Deleting Creator exception");
            }

        }

        public async Task<IResponseTo> GetEntById(int id)
        {

            var Creator = await dbContext.Creators.FindAsync(id);
            return Creator is not null ? _mapper.Map<CreatorResponseTo>(Creator) : throw new ArgumentNullException($"Not found Creator: {id}");


        }

        public IEnumerable<IResponseTo> GetAllEnt()
        {
            try
            {
                return dbContext.Creators.Select(_mapper.Map<CreatorResponseTo>);

            }
            catch
            {
                throw new Exception("Getting all Creators exception");
            }
        }

        public async Task<IResponseTo> UpdateEnt(IRequestTo Dto)
        {
            var CreatorDto = (CreatorRequestTo)Dto;

            if (!Validate(CreatorDto))
            {
                throw new InvalidDataException("Incorrect data for UPDATE");

            }
            var newCreator = _mapper.Map<Creator>(CreatorDto);
            dbContext.Creators.Update(newCreator);
            await dbContext.SaveChangesAsync();
            var Creator = _mapper.Map<CreatorResponseTo>(await dbContext.Creators.FindAsync(newCreator.Id));
            return Creator;

        }

        public bool Validate(CreatorRequestTo CreatorDto)
        {
            if (CreatorDto?.Login?.Length < 2 || CreatorDto?.Login?.Length > 64)
                return false;
            if (CreatorDto?.Lastname?.Length < 2 || CreatorDto?.Lastname?.Length > 64)
                return false;
            if (CreatorDto?.Firstname?.Length < 2 || CreatorDto?.Firstname?.Length > 64)
                return false;
            if (CreatorDto?.Password?.Length < 8 || CreatorDto?.Password?.Length > 128)
                return false;
            return true;
        }

    }
}
