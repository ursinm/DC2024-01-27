using lab2.DB.BaseDBContext;
using Microsoft.EntityFrameworkCore;

namespace lab2.DB
{
    public class PostgreSQLDbContext : BaseDbContext
    {
        public PostgreSQLDbContext()
        {
        }

        public PostgreSQLDbContext(DbContextOptions<BaseDbContext> options)
            : base(options)
        {
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
           => optionsBuilder.UseNpgsql("Host=localhost;Port=5432;Database=distcomp;Username=postgres;Password=chernika_56");
    }
}
