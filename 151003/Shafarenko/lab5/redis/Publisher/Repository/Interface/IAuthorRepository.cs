using Publisher.Entity.Db;
using Publisher.Repository.Interface.Common;

namespace Publisher.Repository.Interface
{
    public interface ICreatorRepository : ICrudRepository<Creator>
    {
        News GetByNewsId(int newsId);
        Task<News> GetByNewsIdAsync(int newsId);
    }
}
