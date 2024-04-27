using Microsoft.EntityFrameworkCore;
using Npgsql;

namespace RV.Models
{
    public class ApplicationContext : DbContext
    {
        public DbSet<User> Users { get; set; } = null!;
        public DbSet<News> News { get; set; } = null!;
        public DbSet<Note> Notes { get; set; } = null!;
        public DbSet<Sticker> Stickers { get; set; } = null!;
        public ApplicationContext(DbContextOptions<ApplicationContext> options)
        : base(options)
        { }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            var masterConnectionString = new NpgsqlConnectionStringBuilder();
            masterConnectionString.Host = "mypostgres";
            masterConnectionString.Port = 5432;
            masterConnectionString.Username = "postgres";
            masterConnectionString.Password = "postgres";
            optionsBuilder.UseNpgsql(masterConnectionString.ConnectionString);
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder
                .Entity<News>()
                .HasMany(c => c.Stickers)
                .WithMany(s => s.News)
                .UsingEntity<NewsSticker>(
                   j => j
                    .HasOne(pt => pt.Sticker)
                    .WithMany(t => t.NewsStickers)
                    .HasForeignKey(pt => pt.stickerId),
                j => j
                    .HasOne(pt => pt.News)
                    .WithMany(p => p.NewsStickers)
                    .HasForeignKey(pt => pt.newsId),
                j =>
                {
                    j.HasKey(t => t.id);
                    j.ToTable("NewsSticker");
                });

            modelBuilder.Entity<User>()
                .ToTable(t => t.HasCheckConstraint("len(login) < 2", "LENGTH(\"login\") > 2"));
            modelBuilder.Entity<User>()
                .ToTable(t => t.HasCheckConstraint("len(password) < 8", "LENGTH(\"password\") > 8"));
            modelBuilder.Entity<User>()
                .ToTable(t => t.HasCheckConstraint("len(firstName) < 2", "LENGTH(\"firstName\") > 2"));
            modelBuilder.Entity<User>()
                .ToTable(t => t.HasCheckConstraint("len(lastName) < 2", "LENGTH(\"lastName\") > 2"));

            modelBuilder.Entity<News>()
                .ToTable(t => t.HasCheckConstraint("len(title) < 2", "LENGTH(\"title\") > 2"));
            modelBuilder.Entity<News>()
                .ToTable(t => t.HasCheckConstraint("len(content) < 4", "LENGTH(\"content\") > 4"));

            modelBuilder.Entity<Note>()
                .ToTable(t => t.HasCheckConstraint("len(content) < 2", "LENGTH(\"content\") > 2"));

            modelBuilder.Entity<Sticker>()
                .ToTable(t => t.HasCheckConstraint("len(name) < 2", "LENGTH(\"name\") > 2"));

            modelBuilder.Entity<User>()
                .HasIndex(p => p.login).IsUnique();
            modelBuilder.Entity<News>()
                .HasIndex(p => p.title).IsUnique();
            modelBuilder.Entity<Sticker>()
                .HasIndex(p => p.name).IsUnique();
        }
    } 
}
