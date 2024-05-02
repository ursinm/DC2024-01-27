using Publisher.Entity.Db;
using Publisher.Repository.Implementation.Common;
using Publisher.Repository.Interface;
using Publisher.Storage.Common;

namespace Publisher.Repository.Implementation
{
    public class CreatorRepository(DbStorage context) : AbstractCrudRepository<Creator>(context), ICreatorRepository
    {
        public News GetByNewsId(int newsId)
        {
            throw new NotImplementedException();
        }

        public Task<News> GetByNewsIdAsync(int newsId)
        {
            throw new NotImplementedException();
        }
    }
}
