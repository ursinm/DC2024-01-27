using AutoMapper;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.EntityFrameworkCore;
using REST.Entity.Db;
using REST.Entity.DTO.RequestTO;
using REST.Entity.DTO.ResponseTO;
using REST.Service.Interface;
using REST.Storage.Common;

namespace REST.Service.Implementation
{
    public class NewsService(DbStorage dbStorage, IMapper mapper) : INewsService
    {
        private readonly DbStorage _context = dbStorage;
        private readonly IMapper _mapper = mapper;

        public async Task<NewsResponseTO> Add(NewsRequestTO news)
        {
            var n = _mapper.Map<News>(news);
            var creator = await _context.Creators.FindAsync(n.CreatorId) ?? throw new ArgumentNullException($"CREATOR not found {n.Creator.Id}");

            if (!Validate(n))
            {
                throw new InvalidDataException("NEWS is not valid");
            }

            n.Creator = creator;
            _context.News.Add(n);
            await _context.SaveChangesAsync();

            return _mapper.Map<NewsResponseTO>(n);
        }

        public IList<NewsResponseTO> GetAll()
        {
            return _context.News.Select(_mapper.Map<NewsResponseTO>).ToList();
        }

        public async Task<NewsResponseTO> Patch(int id, JsonPatchDocument<News> patch)
        {
            var target = await _context.News.FirstAsync(t => t.Id == id)
                ?? throw new ArgumentNullException($"TWEET {id} not found at PATCH {patch}");

            patch.ApplyTo(target);
            await _context.SaveChangesAsync();

            return _mapper.Map<NewsResponseTO>(target);
        }

        public async Task<bool> Remove(int id)
        {
            var target = new News() { Id = id };

            _context.Remove(target);
            await _context.SaveChangesAsync();

            return true;
        }

        public async Task<NewsResponseTO> Update(NewsRequestTO news)
        {
            var n = _mapper.Map<News>(news);

            if (!Validate(n))
            {
                throw new InvalidDataException($"UPDATE invalid data: {news}");
            }

            _context.Update(n);
            await _context.SaveChangesAsync();

            return _mapper.Map<NewsResponseTO>(n);
        }

        public Task<NewsResponseTO> GetNewsByParam(IList<string> markerNames, IList<int> markerIds, string creatorLogin, string title, string content)
        {
            throw new NotImplementedException();
        }

        public async Task<NewsResponseTO> GetByID(int id)
        {

            var c = await _context.News.FirstAsync(t => t.Id == id);

            return c is not null ? _mapper.Map<NewsResponseTO>(c)
                : throw new ArgumentNullException($"Not found NEWS {id}");
        }

        private static bool Validate(News news)
        {
            var titleLen = news.Title.Length;
            var contentLen = news.Content.Length;

            if (titleLen < 2 || titleLen > 64)
            {
                return false;
            }
            if (contentLen < 4 || contentLen > 2048)
            {
                return false;
            }
            if (news.Modified < news.Created)
            {
                return false;
            }
            return true;
        }
    }
}
