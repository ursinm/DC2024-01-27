using Publisher.Entity.Db;
using Publisher.Repository.Implementation.Common;
using Publisher.Repository.Interface;
using Publisher.Storage.Common;

namespace Publisher.Repository.Implementation
{
    public class NewsRepository(DbStorage context) : AbstractCrudRepository<News>(context), INewsRepository
    {
        private readonly DbStorage _context = context;

        public override News Add(News news)
        {
            var creator = new Creator { Id = news.CreatorId };
            _context.Creators.Attach(creator);
            news.Creator = creator;

            return base.Add(news);
        }

        public override async Task<News> AddAsync(News news)
        {
            var creator = new Creator { Id = news.CreatorId };
            _context.Creators.Attach(creator);
            news.Creator = creator;

            return await base.AddAsync(news);
        }
    }
}
