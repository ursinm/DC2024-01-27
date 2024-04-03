using DC.Data;
using DC.Models;
using DC.Repositories.Interfaces;

namespace DC.Repositories.Implementations
{
	public class SqlIssueRepository(ApplicationDbContext dbContext) 
		: BaseSqlRepository<Issue>(dbContext), IIssueRepository
	{

	}
}
