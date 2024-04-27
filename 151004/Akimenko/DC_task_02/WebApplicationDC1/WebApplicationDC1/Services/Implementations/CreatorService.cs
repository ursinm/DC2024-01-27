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
    public class CreatorService(ApplicationContext applicationContext, IMapper mapper) : ICreatorService
    {
        private readonly ApplicationContext _context = applicationContext;
        private readonly IMapper _mapper = mapper;

        public async Task<CreatorResponseTO> Add(CreatorRequestTO creator)
        {
            var existingCreator = await _context.Creators.FirstOrDefaultAsync(c => c.Login == creator.Login);
            if (existingCreator != null)
            {
                throw new InvalidOperationException("Creator with this login already exists");
            }


            var a = _mapper.Map<Creator>(creator);

            if (!Validate(a))
            {
                throw new InvalidDataException("creator is not valid");
            }

            // Устанавливаем значение первичного ключа вручную
            a.Id = GetNextId();

            _context.Add(a);
            await _context.SaveChangesAsync();

            return _mapper.Map<CreatorResponseTO>(a);
        }

        private int GetNextId()
        {
            // Генерируем следующее значение первичного ключа
            // Например, можно использовать максимальное значение id из таблицы Creators и увеличить его на 1
            var maxId = _context.Creators.Max(c => (int?)c.Id) ?? 0;
            return maxId + 1;
        }

        public IList<CreatorResponseTO> GetAll()
        {
            return _context.Creators.Select(_mapper.Map<CreatorResponseTO>).ToList();

        }

        public async Task<CreatorResponseTO> GetByID(int id)
        {
            var a = await _context.Creators.FindAsync(id);

            return a is not null ? _mapper.Map<CreatorResponseTO>(a)
                : throw new ArgumentNullException($"Not found creator {id}");
        }

        public async Task<CreatorResponseTO> GetByStoryID(int storyId)
        {
            var response = await _context.Stories.Include(t => t.Creator).FirstAsync(t => t.Id == storyId);

            return _mapper.Map<CreatorResponseTO>(response.Creator);
        }

        public async Task<CreatorResponseTO> Patch(int id, JsonPatchDocument<Creator> patch)
        {
            var creator = await _context.FindAsync<Creator>(id)
                ?? throw new InvalidDataException($"Creator {id} not found at PATCH {patch}");

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

        private static bool Validate(Creator creator)
        {
            Console.WriteLine($"FirstName length: {creator.FirstName.Length} " + creator.FirstName);
            Console.WriteLine($"LastName length: {creator.LastName.Length} " + creator.LastName);
            Console.WriteLine($"Password length: {creator.Password.Length} " + creator.Password);
            Console.WriteLine($"Login length: {creator.Login.Length} " + creator.Login);
            Console.WriteLine("Creator ID: " + creator.Id);


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
