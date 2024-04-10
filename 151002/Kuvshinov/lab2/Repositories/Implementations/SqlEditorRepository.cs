using DC.Data;
using DC.Models;
using DC.Repositories.Interfaces;

namespace DC.Repositories.Implementations
{
    public class SqlEditorRepository : BaseSqlRepository<Editor>, IEditorRepository
    {
        public SqlEditorRepository(ApplicationDbContext dbContext) : base(dbContext)
        {

        }
    }
}
