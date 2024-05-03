using DC_Lab1.Models;
using Microsoft.EntityFrameworkCore;

namespace DC_Lab1.DB.BaseDBContext
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


        public virtual DbSet<Editor> Editors { get; set; }

        public virtual DbSet<Post> Posts { get; set; }

        public virtual DbSet<Sticker> Stickers { get; set; }

        public virtual DbSet<Tweet> Tweets { get; set; }

        public virtual DbSet<TweetSticker> TweetStickers { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Editor>(entity =>
            {
                entity.ToTable("tbl_editor");
                entity.HasIndex(e => e.Login, "IX_Editors_login").IsUnique();

                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.Firstname).HasColumnName("firstname");
                entity.Property(e => e.Lastname).HasColumnName("lastname");
                entity.Property(e => e.Login).HasColumnName("login");
                entity.Property(e => e.Password).HasColumnName("password");
            });

            modelBuilder.Entity<Post>(entity =>
            {
                entity.ToTable("tbl_post");
                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.Content).HasColumnName("content");
                entity.Property(e => e.TweetId).HasColumnName("tweetId");

                entity.HasOne(d => d.Tweet).WithMany(p => p.Posts).HasForeignKey(d => d.TweetId);
            });

            modelBuilder.Entity<Sticker>(entity =>
            {
                entity.ToTable("tbl_sticker");
                entity.HasIndex(e => e.Id, "IX_Stickers_id").IsUnique();

                entity.HasIndex(e => e.Name, "IX_Stickers_name").IsUnique();

                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.Name).HasColumnName("name");
            });

            modelBuilder.Entity<Tweet>(entity =>
            {
                entity.ToTable("tbl_tweet");
                entity.HasIndex(e => e.Title, "IX_Tweets_title").IsUnique();

                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.Content).HasColumnName("content");
                entity.Property(e => e.Created).HasColumnName("created");
                entity.Property(e => e.Modified).HasColumnName("modified");
                entity.Property(e => e.Title).HasColumnName("title");

                entity.HasOne(d => d.Editor).WithMany(p => p.Tweets).HasForeignKey(d => d.EditorId);
                entity.Property(e => e.EditorId).HasColumnName("editor_id");
            });

            modelBuilder.Entity<TweetSticker>(entity =>
            {
                entity.ToTable("tbl_tweetSticker");

                entity.Property(e => e.Id)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("id");
                entity.Property(e => e.StickerId).HasColumnName("stickerId");
                entity.Property(e => e.TweetId).HasColumnName("tweetId");

                entity.HasOne(d => d.Sticker).WithMany(p => p.TweetStickers).HasForeignKey(d => d.StickerId);

                entity.HasOne(d => d.Tweet).WithMany(p => p.TweetStickers).HasForeignKey(d => d.TweetId);
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
