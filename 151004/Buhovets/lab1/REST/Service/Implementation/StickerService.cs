using AutoMapper;
using Microsoft.AspNetCore.JsonPatch;
using REST.Entity.Db;
using REST.Entity.DTO.RequestTO;
using REST.Entity.DTO.ResponseTO;
using REST.Service.Interface;
using REST.Storage.Common;

namespace REST.Service.Implementation
{
    public class StickerService(DbStorage dbStorage, IMapper mapper) : IStickerService
    {
        private readonly DbStorage _context = dbStorage;
        private readonly IMapper _mapper = mapper;

        public async Task<StickerResponseTO> Add(StickerRequestTO marker)
        {
            var m = _mapper.Map<Sticker>(marker);

            if (!Validate(m))
            {
                throw new InvalidDataException("MARKER is not valid");
            }

            _context.Markers.Add(m);
            await _context.SaveChangesAsync();

            return _mapper.Map<StickerResponseTO>(m);
        }

        public IList<StickerResponseTO> GetAll()
        {
            return _context.Markers.Select(_mapper.Map<StickerResponseTO>).ToList();
        }

        public async Task<StickerResponseTO> Patch(int id, JsonPatchDocument<Sticker> patch)
        {
            var target = await _context.FindAsync<Sticker>(id)
                ?? throw new InvalidDataException($"MARKER {id} not found at PATCH {patch}");

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

        public async Task<StickerResponseTO> Update(StickerRequestTO marker)
        {
            var m = _mapper.Map<Sticker>(marker);

            if (!Validate(m))
            {
                throw new InvalidDataException($"UPDATE invalid data: {marker}");
            }

            _context.Update(m);
            await _context.SaveChangesAsync();

            return _mapper.Map<StickerResponseTO>(m);
        }

        public async Task<StickerResponseTO> GetByID(int id)
        {
            var a = await _context.Markers.FindAsync(id);

            return a is not null ? _mapper.Map<StickerResponseTO>(a)
                : throw new ArgumentNullException($"Not found MARKER {id}");
        }

        public Task<IList<StickerResponseTO>> GetByTweetID(int tweetId)
        {
            throw new NotImplementedException();
        }

        private static bool Validate(Sticker marker)
        {
            var nameLen = marker.Name.Length;

            if (nameLen < 2 || nameLen > 32)
            {
                return false;
            }
            return true;
        }
    }
}
