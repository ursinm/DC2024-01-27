//using Microsoft.EntityFrameworkCore;
//using WebApplicationDC1.Entity.DataModel;
//using WebApplicationDC1.Repositories;

//namespace RV.Repositories.SQLRepositories
//{
//    public class SQLStoriesRepository : IRepository<Story>
//    {
//        private ApplicationContext _dbContext;

//        public SQLStoriesRepository(ApplicationContext dbContext)
//        {
//            _dbContext = dbContext;
//        }

//        public Story Create(Story item)
//        {
//            _dbContext.Add(item);
//            _dbContext.SaveChanges();
//            return item;
//        }

//        public int Delete(int id)
//        {
//            int res = _dbContext.Stories.Where(n => n.Id == id).ExecuteDelete();
//            _dbContext.SaveChanges();
//            return res;
//        }

//        public Story Get(int id)
//        {
//            var res = _dbContext.Stories.Where(n => n.Id == id).ToList();
//            Story n;
//            if (res.Count > 0)
//            {
//                n = res[0];
//                return n;
//            }
//            else return null;
//        }

//        public List<Story> GetAll()
//        {
//            return _dbContext.Stories.ToList();
//        }

//        public Story Update(Story item)
//        {
//            var res = _dbContext.Stories.Where(n => n.Id == item.Id).ToList();
//            Story n;
//            if (res.Count > 0)
//            {
//                n = res[0];
//            }
//            else return null;
//            if (item.Id != null)
//            {
//                n.Id = (int)item.Id;
//            }
//            if (item.Title != null)
//            {
//                n.Title = item.Title;
//            }
//            if (item.Content != null)
//            {
//                n.Content = item.Content;
//            }
//            n.Modified = DateTime.UtcNow;
//            _dbContext.Update(n);
//            _dbContext.SaveChanges();
//            return n;
//        }
//    }
//}
