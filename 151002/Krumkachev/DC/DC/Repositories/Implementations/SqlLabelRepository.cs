using DC.Data;
using DC.Models;
using DC.Repositories.Interfaces;

namespace DC.Repositories.Implementations
{
	public class SqlLabelRepository(ApplicationDbContext dbContext)
		: BaseSqlRepository<Label>(dbContext), ILabelRepository
	{
	}
}
