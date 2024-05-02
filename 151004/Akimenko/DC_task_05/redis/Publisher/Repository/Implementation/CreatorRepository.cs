using Publisher.Entity.Db;
using Publisher.Repository.Implementation.Common;
using Publisher.Repository.Interface;
using Publisher.Storage.Common;

namespace Publisher.Repository.Implementation
{
    public class creatorRepository(DbStorage context) : AbstractCrudRepository<creator>(context), IcreatorRepository
    {
        public story GetBystoryId(int storyId)
        {
            throw new NotImplementedException();
        }

        public Task<story> GetBystoryIdAsync(int storyId)
        {
            throw new NotImplementedException();
        }
    }
}
