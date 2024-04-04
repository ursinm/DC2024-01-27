using Lab1.DB.BaseDBContext;
using Microsoft.EntityFrameworkCore;

namespace Lab1.DB
{
    public class SqLiteDbContext : BaseContext
    {
        public SqLiteDbContext()
        {
        }

        public SqLiteDbContext(DbContextOptions<BaseContext> options)
            : base(options)
        {
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
           => optionsBuilder.UseSqlite("DataSource=file::memory:?cache=shared");
    }
}
