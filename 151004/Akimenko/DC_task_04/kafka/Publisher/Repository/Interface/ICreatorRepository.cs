using Publisher.Entity.Db;
using Publisher.Repository.Interface.Common;

namespace Publisher.Repository.Interface
{
    public interface IcreatorRepository : ICrudRepository<creator>
    {
        story GetBystoryId(int storyId);
        Task<story> GetBystoryIdAsync(int storyId);
    }
}
