using Lab1.Models;
using Microsoft.EntityFrameworkCore;

namespace Lab1.DB.BaseDBContext
{
    public partial class BaseContext : DbContext
    {
        public BaseContext()
        {

            Database.EnsureCreated();
        }

        public BaseContext(DbContextOptions<BaseContext> options)
            : base(options)
        {
            Database.EnsureCreated();
        }


        public virtual DbSet<Creator> Creators { get; set; }

        public virtual DbSet<Note> Notes { get; set; }

        public virtual DbSet<Marker> Markers { get; set; }

        public virtual DbSet<Tweet> Tweets { get; set; }

        public virtual DbSet<TweetMarker> TweetMarkers { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Creator>(entity =>
            {
                entity.ToTable("tbl_Creator");
                entity.HasIndex(e => e.Login, "IX_Creators_login").IsUnique();

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
                entity.Property(e => e.TweetId).HasColumnName("tweetId");

                entity.HasOne(d => d.Tweet).WithMany(p => p.Notes).HasForeignKey(d => d.TweetId);
            });

            modelBuilder.Entity<Marker>(entity =>
            {
                entity.ToTable("tbl_Marker");
                entity.HasIndex(e => e.Id, "IX_Markers_id").IsUnique();

                entity.HasIndex(e => e.Name, "IX_Markers_name").IsUnique();

                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.Name).HasColumnName("name");
            });

            modelBuilder.Entity<Tweet>(entity =>
            {
                entity.ToTable("tbl_Tweet");
                entity.HasIndex(e => e.Title, "IX_Tweets_title").IsUnique();

                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.Content).HasColumnName("content");
                entity.Property(e => e.Created).HasColumnName("created");
                entity.Property(e => e.Modified).HasColumnName("modified");
                entity.Property(e => e.Title).HasColumnName("title");

                entity.HasOne(d => d.Creator).WithMany(p => p.Tweets).HasForeignKey(d => d.CreatorId);
            });

            modelBuilder.Entity<TweetMarker>(entity =>
            {
                entity.ToTable("tbl_TweetMarker");

                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.MarkerId).HasColumnName("MarkerId");
                entity.Property(e => e.TweetId).HasColumnName("tweetId");

                entity.HasOne(d => d.Marker).WithMany(p => p.TweetMarkers).HasForeignKey(d => d.MarkerId);

                entity.HasOne(d => d.Tweet).WithMany(p => p.TweetMarkers).HasForeignKey(d => d.TweetId);
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
