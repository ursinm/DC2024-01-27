using DC.Data;
using DC.Models;
using DC.Repositories.Implementations;
using DC.Repositories.Interfaces;

namespace DC.Repositories.Implementations
{
	public class SqlNoteRepository : BaseSqlRepository<Note>, INoteRepository
    {
		public SqlNoteRepository(ApplicationDbContext dbContext) : base(dbContext)
        {

        }
    }
}

