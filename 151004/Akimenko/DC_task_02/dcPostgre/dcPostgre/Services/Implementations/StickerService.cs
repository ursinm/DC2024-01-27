using AutoMapper;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.EntityFrameworkCore;
using WebApplicationDC1.Entity.DataModel;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;
using WebApplicationDC1.Repositories;
using WebApplicationDC1.Services.Interfaces;

namespace WebApplicationDC1.Services.Implementations
{
    public class StickerService(ApplicationContext applicationContext, IMapper mapper) : IStickerService
    {
        private readonly ApplicationContext _context = applicationContext;
        private readonly IMapper _mapper = mapper;

        public async Task<StickerResponseTO> Add(StickerRequestTO Sticker)
        {
            var m = _mapper.Map<Sticker>(Sticker);

            if (!Validate(m))
            {
                throw new InvalidDataException("Sticker is not valid");
            }

            _context.Stickers.Add(m);
            await _context.SaveChangesAsync();

            return _mapper.Map<StickerResponseTO>(m);
        }


        //public async Task<StickerResponseTO> Add(StickerRequestTO Sticker)
        //{
        //    var m = _mapper.Map<Sticker>(Sticker);

        //    if (!Validate(m))
        //    {
        //        throw new InvalidDataException("Sticker is not valid");
        //    }

        //    _context.Stickers.Add(m);
        //    await _context.SaveChangesAsync();

        //    return _mapper.Map<StickerResponseTO>(m);
        //}

        public IList<StickerResponseTO> GetAll()
        {
            return _context.Stickers.Select(_mapper.Map<StickerResponseTO>).ToList();
        }

        public async Task<StickerResponseTO> Patch(int id, JsonPatchDocument<Sticker> patch)
        {
            var target = await _context.FindAsync<Sticker>(id)
                ?? throw new InvalidDataException($"Sticker {id} not found at PATCH {patch}");

            patch.ApplyTo(target);
            await _context.SaveChangesAsync();

            return _mapper.Map<StickerResponseTO>(target);
        }

        public async Task<bool> Remove(int id)
        {
            var target = new Sticker() { Id = id };

            _context.Remove(target);
            await _context.SaveChangesAsync();

            return true;
        }

        public async Task<StickerResponseTO> Update(StickerRequestTO Sticker)
        {
            var existingSticker = await _context.Stickers.FindAsync(Sticker.Id);

            if (existingSticker == null)
            {
                throw new ArgumentNullException($"Sticker {Sticker.Id} not found");
            }

            _mapper.Map(Sticker, existingSticker);

            if (!Validate(existingSticker))
            {
                throw new InvalidDataException($"UPDATE invalid data: {Sticker}");
            }

            _context.Entry(existingSticker).State = EntityState.Modified;
            await _context.SaveChangesAsync();

            return _mapper.Map<StickerResponseTO>(existingSticker);
        }



        public Task<IList<StickerResponseTO>> GetByStoryID(int storyId)
        {
            throw new NotImplementedException();
        }

        public async Task<StickerResponseTO> GetByID(int id)
        {
            var a = await _context.Stickers.FindAsync(id);

            return a is not null ? _mapper.Map<StickerResponseTO>(a)
                : throw new ArgumentNullException($"Not found Sticker {id}");
        }

        private static bool Validate(Sticker Sticker)
        {
            var nameLen = Sticker.Name.Length;

            if (nameLen < 2 || nameLen > 32)
            {
                return false;
            }
            return true;
        }

        
    }
}
