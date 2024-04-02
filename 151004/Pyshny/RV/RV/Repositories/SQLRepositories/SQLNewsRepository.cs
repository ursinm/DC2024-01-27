using Microsoft.EntityFrameworkCore;
using RV.Models;

namespace RV.Repositories.SQLRepositories
{
    public class SQLNewsRepository : IRepository<News>
    {
        private ApplicationContext _dbContext;

        public SQLNewsRepository(ApplicationContext dbContext)
        {
            _dbContext = dbContext;
        }

        public News Create(News item)
        {
            _dbContext.Add(item);
            _dbContext.SaveChanges();
            return item;
        }

        public int Delete(int id)
        {
            int res = _dbContext.News.Where(n => n.id == id).ExecuteDelete();
            _dbContext.SaveChanges();
            return res;
        }

        public News Get(int id)
        {
            var res = _dbContext.News.Where(n => n.id == id).ToList();
            News n;
            if (res.Count > 0)
            {
                n = res[0];
                return n;
            }
            else return null;
        }

        public List<News> GetAll()
        {
            return _dbContext.News.ToList();
        }

        public News Update(News item)
        {
            var res = _dbContext.News.Where(n => n.id == item.id).ToList();
            News n;
            if (res.Count > 0)
            {
                n = res[0];
            }
            else return null;
            if (item.userId != null)
            {
                n.userId = (int)item.userId;
            }
            if (item.title != null)
            {
                n.title = item.title;
            }
            if (item.content != null)
            {
                n.content = item.content;
            }
            n.modified = DateTime.UtcNow;
            _dbContext.Update(n);
            _dbContext.SaveChanges();
            return n;
        }
    }
}
