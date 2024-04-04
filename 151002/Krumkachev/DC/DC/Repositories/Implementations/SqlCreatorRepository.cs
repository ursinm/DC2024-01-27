using DC.Data;
using DC.Models;
using DC.Repositories.Interfaces;

namespace DC.Repositories.Implementations
{
	public class SqlCreatorRepository(ApplicationDbContext dbContext) 
		: BaseSqlRepository<Creator>(dbContext), ICreatorRepository
	{
		
	}
}
