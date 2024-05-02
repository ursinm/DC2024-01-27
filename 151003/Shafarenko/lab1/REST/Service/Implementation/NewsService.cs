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

        public async Task<NewsResponseTO> Add(NewsRequestTO tweet)
        {
            var t = _mapper.Map<News>(tweet);
            var author = await _context.Creators.FindAsync(t.Creator.Id) ?? throw new ArgumentNullException($"AUTHOR not found {t.Creator.Id}");

            if (!Validate(t))
            {
                throw new InvalidDataException("TWEET is not valid");
            }

            t.Creator = author;
            _context.News.Add(t);
            await _context.SaveChangesAsync();

            return _mapper.Map<NewsResponseTO>(t);
        }

        public IList<NewsResponseTO> GetAll()
        {
            return _context.News.Include(t => t.Creator).Select(_mapper.Map<NewsResponseTO>).ToList();
        }

        public async Task<NewsResponseTO> Patch(int id, JsonPatchDocument<News> patch)
        {
            var target = await _context.News.Include(t => t.Creator).FirstAsync(t => t.Id == id)
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

        public async Task<NewsResponseTO> Update(NewsRequestTO tweet)
        {
            var t = _mapper.Map<News>(tweet);

            if (!Validate(t))
            {
                throw new InvalidDataException($"UPDATE invalid data: {tweet}");
            }

            _context.Update(t);
            await _context.SaveChangesAsync();

            return _mapper.Map<NewsResponseTO>(t);
        }

        public Task<NewsResponseTO> GetNewsByParam(IList<string> markerNames, IList<int> markerIds, string authorLogin, string title, string content)
        {
            throw new NotImplementedException();
        }

        public async Task<NewsResponseTO> GetByID(int id)
        {

            var a = await _context.News.Include(t => t.Creator).FirstAsync(t => t.Id == id);

            return a is not null ? _mapper.Map<NewsResponseTO>(a)
                : throw new ArgumentNullException($"Not found TWEET {id}");
        }

        private static bool Validate(News tweet)
        {
            var titleLen = tweet.Title.Length;
            var contentLen = tweet.Content.Length;

            if (titleLen < 2 || titleLen > 64)
            {
                return false;
            }
            if (contentLen < 4 || contentLen > 2048)
            {
                return false;
            }
            if (tweet.Modified < tweet.Created)
            {
                return false;
            }
            return true;
        }
    }
}
