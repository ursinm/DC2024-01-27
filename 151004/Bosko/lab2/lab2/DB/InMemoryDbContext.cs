using lab2.DB.BaseDBContext;
using Microsoft.EntityFrameworkCore;

namespace lab2.DB
{
    public class InMemoryDbContext : BaseDbContext
    {
        public InMemoryDbContext()
        {
        }

        public InMemoryDbContext(DbContextOptions<BaseDbContext> options)
            : base(options)
        {
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
           => optionsBuilder.UseSqlite("DataSource=file::memory:?cache=shared");
    }
}
