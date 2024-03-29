using Microsoft.EntityFrameworkCore;
using REST.Entity.Db;

namespace REST.Storage.Common
{
    public abstract class DbStorage : DbContext
    {
        public DbSet<Creator> Creators { get; set; }
        public DbSet<Marker> Markers { get; set; }
        public DbSet<Comment> Comments { get; set; }
        public DbSet<Issue> Issues { get; set; }

        public DbStorage()
        {
            Database.EnsureCreated();
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<Creator>(entity =>
            {
                entity.ToTable("tbl_creators");

                entity.HasKey(e => e.Id);

                entity.Property(c => c.Id).ValueGeneratedOnAdd();
                entity.Property(c => c.Password).IsRequired().HasMaxLength(128);
                entity.Property(c => c.FirstName).IsRequired().HasMaxLength(64).IsUnicode();
                entity.Property(c => c.LastName).IsRequired().HasMaxLength(64).IsUnicode();
                entity.Property(c => c.Login).IsRequired().HasMaxLength(64).IsUnicode();

                entity.HasIndex(c => c.Login).IsUnique();

                entity.HasMany(c => c.Issues).WithOne(t => t.Creator);
            });

            modelBuilder.Entity<Comment>((entity) =>
            {
                entity.ToTable("tbl_comments");

                entity.HasKey(p => p.Id);

                entity.Property(p => p.Id).ValueGeneratedOnAdd();
                entity.Property(p => p.Content).IsRequired().HasMaxLength(2048);
                entity.Property(p => p.IssueId).IsRequired();

                entity.HasOne(p => p.Issue).WithMany(t => t.Comments);
            });

            modelBuilder.Entity<Issue>(entity =>
            {
                entity.ToTable("tbl_issues");

                entity.HasKey(i => i.Id);

                entity.Property(i => i.Id).ValueGeneratedOnAdd();
                entity.Property(i => i.Content).IsRequired().HasMaxLength(2048);
                entity.Property(i => i.Created).IsRequired();
                entity.Property(i => i.Modified).IsRequired();
                entity.Property(i => i.Title).IsRequired().HasMaxLength(64);

                entity.HasIndex(i => i.Title).IsUnique();
            });

            modelBuilder.Entity<Marker>(entity =>
            {
                entity.ToTable("tbl_markers");

                entity.HasKey(m => m.Id);
                entity.Property(m => m.Id).ValueGeneratedOnAdd();
                entity.Property(m => m.Name).IsRequired().HasMaxLength(32);

                entity.HasIndex(m => m.Name).IsUnique();

                entity.HasMany(m => m.Issues).WithMany(t => t.Markers);
            });
        }
    }
}
