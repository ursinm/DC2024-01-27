using Microsoft.EntityFrameworkCore;
using lab2.Models;

namespace lab2.DB.BaseDBContext
{
    public partial class BaseDbContext : DbContext
    {
        public BaseDbContext()
        {
            Database.EnsureCreated();
        }

        public BaseDbContext(DbContextOptions<BaseDbContext> options)
            : base(options)
        {
            Database.EnsureCreated();
        }

        public virtual DbSet<User> Users { get; set; }

        public virtual DbSet<Note> Notes { get; set; }

        public virtual DbSet<Label> Labels { get; set; }

        public virtual DbSet<News> News { get; set; }

        public virtual DbSet<NewsLabel> NewsLabels { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<User>(entity =>
            {
                entity.ToTable("tbl_User");
                entity.HasIndex(e => e.Login, "IX_Users_login").IsUnique();

                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.Firstname).HasColumnName("firstname");
                entity.Property(e => e.Lastname).HasColumnName("lastname");
                entity.Property(e => e.Login).HasColumnName("login");
                entity.Property(e => e.Password).HasColumnName("password");
            });

            modelBuilder.Entity<Note>(entity =>
            {
                entity.ToTable("tbl_Note");
                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.Content).HasColumnName("content");
                entity.Property(e => e.NewsId).HasColumnName("newsId");

                entity.HasOne(d => d.News).WithMany(p => p.Notes).HasForeignKey(d => d.NewsId);
            });

            modelBuilder.Entity<Label>(entity =>
            {
                entity.ToTable("tbl_Label");
                entity.HasIndex(e => e.Id, "IX_Labels_id").IsUnique();

                entity.HasIndex(e => e.Name, "IX_Labels_name").IsUnique();

                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.Name).HasColumnName("name");
            });

            modelBuilder.Entity<News>(entity =>
            {
                entity.ToTable("tbl_News");
                entity.HasIndex(e => e.Title, "IX_News_title").IsUnique();

                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.Content).HasColumnName("content");
                entity.Property(e => e.Created).HasColumnName("created");
                entity.Property(e => e.Modified).HasColumnName("modified");
                entity.Property(e => e.Title).HasColumnName("title");

                entity.HasOne(d => d.User).WithMany(p => p.News).HasForeignKey(d => d.UserId);
            });

            modelBuilder.Entity<NewsLabel>(entity =>
            {
                entity.ToTable("tbl_NewsLabel");

                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.LabelId).HasColumnName("labelId");
                entity.Property(e => e.NewsId).HasColumnName("newsId");

                entity.HasOne(d => d.Label).WithMany(p => p.NewsLabels).HasForeignKey(d => d.LabelId);

                entity.HasOne(d => d.News).WithMany(p => p.NewsLabels).HasForeignKey(d => d.NewsId);
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
