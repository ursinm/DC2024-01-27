using System.Collections.Generic;
using System.Reflection.Emit;
using WebApplicationDC1.Entity.DataModel;
using Microsoft.EntityFrameworkCore;
using static System.Net.Mime.MediaTypeNames;


namespace WebApplicationDC1.Repositories
{
    public class ApplicationContext : DbContext
    {
        //public DbSet<Creator> Users => Set<Creator>();
        //public ApplicationContext() => Database.EnsureCreated();

        //protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        //{
        //    optionsBuilder.UseSqlite("Data Source=helloapp.db");
        //}

        public DbSet<Creator> Creators { get; set; }
        public DbSet<Sticker> Stickers { get; set; }
        public DbSet<Post> Posts { get; set; }
        public DbSet<Story> Stories { get; set; }

        public ApplicationContext()
        {
            Database.EnsureCreated();
            //Database.EnsureDeleted();
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            modelBuilder.HasDefaultSchema("public");


            modelBuilder.Entity<Creator>(entity =>
            {
                entity.ToTable("tbl_creators");

                entity.HasKey(e => e.Id);

                entity.Property(a => a.Id).ValueGeneratedOnAdd();
                entity.Property(a => a.Password).IsRequired().HasMaxLength(128);
                entity.Property(a => a.FirstName).IsRequired().HasMaxLength(64).IsUnicode();
                entity.Property(a => a.LastName).IsRequired().HasMaxLength(64).IsUnicode();
                entity.Property(a => a.Login).IsRequired().HasMaxLength(64).IsUnicode();

                entity.HasIndex(a => a.Login).IsUnique();

                entity.HasMany(a => a.Stories).WithOne(t => t.Creator);
            });

            modelBuilder.Entity<Post>((entity) =>
            {
                entity.ToTable("tbl_posts");

                entity.HasKey(p => p.Id);

                entity.Property(p => p.Id).ValueGeneratedOnAdd();
                entity.Property(p => p.Content).IsRequired().HasMaxLength(2048);
                entity.Property(p => p.StoryId).IsRequired();

                entity.HasOne(p => p.Story).WithMany(t => t.Posts);
            });

            modelBuilder.Entity<Story>(entity =>
            {
                entity.ToTable("tbl_storys");

                entity.HasKey(t => t.Id);

                entity.Property(a => a.Id).ValueGeneratedOnAdd();
                entity.Property(t => t.Content).IsRequired().HasMaxLength(2048);
                entity.Property(t => t.Created).IsRequired();
                entity.Property(t => t.Modified).IsRequired();
                entity.Property(t => t.Title).IsRequired().HasMaxLength(64);

                entity.HasIndex(t => t.Title).IsUnique();
            });

            modelBuilder.Entity<Sticker>(entity =>
            {
                entity.ToTable("tbl_stickers");

                entity.HasKey(m => m.Id);
                entity.Property(m => m.Id).ValueGeneratedOnAdd();
                entity.Property(m => m.Name).IsRequired().HasMaxLength(32);

                entity.HasIndex(m => m.Name).IsUnique();

                entity.HasMany(m => m.Stories).WithMany(t => t.Stickers);
            });
        }
    }
}

