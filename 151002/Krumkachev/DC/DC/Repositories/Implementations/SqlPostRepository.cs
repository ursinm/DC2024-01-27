using DC.Data;
using DC.Models;
using DC.Repositories.Interfaces;

namespace DC.Repositories.Implementations
{
	public class SqlPostRepository(ApplicationDbContext dbContext)
		: BaseSqlRepository<Post>(dbContext), IPostRepository
	{
	}
}
