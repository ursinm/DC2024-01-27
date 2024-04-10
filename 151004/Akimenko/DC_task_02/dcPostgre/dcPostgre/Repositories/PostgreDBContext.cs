using Microsoft.EntityFrameworkCore;

namespace WebApplicationDC1.Repositories
{
    public class PostgreDBContext : ApplicationContext
    {
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseNpgsql("Host=localhost;Port=5432;Database=postgres;Username=Inten");
        }
    }
}
