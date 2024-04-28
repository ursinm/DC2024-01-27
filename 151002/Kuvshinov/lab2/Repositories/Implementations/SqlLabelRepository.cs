using DC.Data;
using DC.Models;
using DC.Repositories.Implementations;
using DC.Repositories.Interfaces;

namespace DC.Repositories.Implementations
{
	public class SqlLabelRepository : BaseSqlRepository<Label>, ILabelRepository
    {
        public SqlLabelRepository(ApplicationDbContext dbContext) : base(dbContext)
        {

        }
    }
}
