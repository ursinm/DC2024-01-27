using DC_Lab1.DTO.Interface;
using DC_Lab1.DTO;
using DC_Lab1.Services.Interfaces;
using Microsoft.EntityFrameworkCore;
using System.Globalization;
using AutoMapper;
using DC_Lab1.Models;

namespace DC_Lab1.Services
{
    public class StickerService(IMapper _mapper, LabDbContext dbContext) : IStickerService
    {
        public async Task<IResponseTo> CreateEnt(IRequestTo Dto)
        {
            var StickerDto = (StickerRequestTo)Dto;

            try
            {

                var Sticker = _mapper.Map<Sticker>(StickerDto);
                dbContext.Stickers.Add(Sticker);
                await dbContext.SaveChangesAsync();
                var response = _mapper.Map<StickerResponseTo>(Sticker);
                return response;



            }
            catch
            {
                throw new ArgumentException();
            }
        }

        public async Task DeleteEnt(int id)
        {
            try
            {
                var Sticker = await dbContext.Stickers.FindAsync(id);
                dbContext.Stickers.Remove(Sticker!);
                await dbContext.SaveChangesAsync();
                return;
            }
            catch
            {
                throw new ArgumentException();
            }
        }

        public async Task<IResponseTo> GetEntById(int id)
        {
            try
            {
                return _mapper.Map<StickerResponseTo>(await dbContext.Stickers.FindAsync(id));

            }
            catch
            {
                throw new ArgumentException();
            }
        }

        public IEnumerable<IResponseTo> GetAllEnt()
        {
            try
            {
                return dbContext.Stickers.Select(_mapper.Map<StickerResponseTo>);

            }
            catch
            {
                throw new ArgumentException();
            }
        }

        public async Task<IResponseTo> UpdateEnt(IRequestTo Dto)
        {
            var StickerDto = (StickerRequestTo)Dto;
            try
            {

                var newSticker = _mapper.Map<Sticker>(StickerDto);
                dbContext.Stickers.Update(newSticker);
                await dbContext.SaveChangesAsync();
                var Sticker = _mapper.Map<StickerResponseTo>(await dbContext.Stickers.FindAsync(newSticker.Id));
                return Sticker;

            }
            catch
            {

                throw new ArgumentException();
            }
        }


    }
}
