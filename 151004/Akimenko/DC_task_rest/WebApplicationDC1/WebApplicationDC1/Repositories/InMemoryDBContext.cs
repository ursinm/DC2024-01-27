using Microsoft.EntityFrameworkCore;

namespace WebApplicationDC1.Repositories
{
    public class InMemoryDBContext : ApplicationContext
    {
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            //optionsBuilder.UseSqlite("Data Source=helloapp.db");
            optionsBuilder.UseSqlite("Data Source=D:\\Bugor\\6sem\\DistibutedComputing\\projects\\rest\\WebApplicationDC1\\WebApplicationDC1\\helloapp.db");
        }
    }
}
