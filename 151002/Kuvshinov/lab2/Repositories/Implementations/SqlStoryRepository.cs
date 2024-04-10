using DC.Data;
using DC.Models;
using DC.Repositories.Implementations;
using DC.Repositories.Interfaces;

namespace DC.Repositories.Implementations
{
	public class SqlStoryRepository: BaseSqlRepository<Story>, IStoryRepository
    {
		public SqlStoryRepository(ApplicationDbContext dbContext) : base(dbContext)
        {

        }
    }
}
