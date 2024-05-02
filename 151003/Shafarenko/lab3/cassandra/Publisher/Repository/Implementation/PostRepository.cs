using Publisher.Entity.Db;
using Publisher.Repository.Implementation.Common;
using Publisher.Repository.Interface;
using Publisher.Storage.Common;

namespace Publisher.Repository.Implementation
{
    public class PostRepository(DbStorage context) : AbstractCrudRepository<Post>(context), IPostRepository
    {
        private readonly DbStorage _context = context;

        public override Post Add(Post post)
        {
            var news = new News { Id = post.NewsId };
            _context.News.Attach(news);
            post.News = news;

            return base.Add(post);
        }

        public override async Task<Post> AddAsync(Post post)
        {
            var news = new News { Id = post.NewsId };
            _context.News.Attach(news);
            post.News = news;

            return await base.AddAsync(post);
        }
    }
}
