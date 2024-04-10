using AutoMapper;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using REST.Entity.Db;
using REST.Entity.DTO.RequestTO;
using REST.Entity.DTO.ResponseTO;
using REST.Service.Interface;
using REST.Storage.Common;

namespace REST.Service.Implementation
{
    public class CreatorService(DbStorage dbStorage, IMapper mapper) : ICreatorService
    {
        private readonly DbStorage _context = dbStorage;
        private readonly IMapper _mapper = mapper;

        public async Task<CreatorResponseTO> Add(CreatorRequestTO creator)
        {
            var a = _mapper.Map<Creator>(creator);

            if (!Validate(a))
            {
                throw new InvalidDataException("Creator is not valid");
            }

            _context.Add(a);
            await _context.SaveChangesAsync();

            return _mapper.Map<CreatorResponseTO>(a);
        }

        public IList<CreatorResponseTO> GetAll()
        {
            return _context.Creators.Select(_mapper.Map<CreatorResponseTO>).ToList();
        }

        public async Task<CreatorResponseTO> Patch(int id, JsonPatchDocument<Creator> patch)
        {
            var creator = await _context.FindAsync<Creator>(id)
                ?? throw new InvalidDataException($"CREATOR {id} not found at PATCH {patch}");

            patch.ApplyTo(creator);
            await _context.SaveChangesAsync();

            return _mapper.Map<CreatorResponseTO>(creator);
        }

        public async Task<bool> Remove(int id)
        {
            var a = new Creator() { Id = id };

            _context.Remove(a);
            await _context.SaveChangesAsync();

            return true;
        }

        public async Task<CreatorResponseTO> Update(CreatorRequestTO creator)
        {
            var a = _mapper.Map<Creator>(creator);

            if (!Validate(a))
            {
                throw new InvalidDataException($"UPDATE invalid data: {creator}");
            }

            _context.Update(a);
            await _context.SaveChangesAsync();

            return _mapper.Map<CreatorResponseTO>(a);
        }

        public async Task<CreatorResponseTO> GetByID([FromRoute] int id)
        {
            var a = await _context.Creators.FindAsync(id);

            return a is not null ? _mapper.Map<CreatorResponseTO>(a)
                : throw new ArgumentNullException($"Not found CREATOR {id}");
        }

        public async Task<CreatorResponseTO> GetByNewsID(int newsId)
        {
            var response = await _context.News.Include(t => t.Creator).FirstAsync(t => t.Id == newsId);

            return _mapper.Map<CreatorResponseTO>(response.Creator);
        }

        private static bool Validate(Creator creator)
        {
            var fnameLen = creator.FirstName.Length;
            var lnameLen = creator.LastName.Length;
            var passLen = creator.Password.Length;
            var loginLen = creator.Login.Length;

            if (fnameLen < 2 || fnameLen > 64)
            {
                return false;
            }
            if (lnameLen < 2 || fnameLen > 64)
            {
                return false;
            }
            if (passLen < 8 || passLen > 128)
            {
                return false;
            }
            if (loginLen < 2 || loginLen > 64)
            {
                return false;
            }
            return true;
        }
    }
}
