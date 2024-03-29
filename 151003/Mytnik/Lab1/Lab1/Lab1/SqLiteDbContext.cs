using System;
using System.Collections.Generic;
using Lab1.Models;
using Microsoft.EntityFrameworkCore;

namespace Lab1;

public partial class SqLiteDbContext : DbContext
{
    public SqLiteDbContext()
    {
        Database.EnsureCreated();
    }

    public SqLiteDbContext(DbContextOptions<SqLiteDbContext> options)
        : base(options)
    {
        Database.EnsureCreated();
    }

    public virtual DbSet<Creator> Creators { get; set; }

    public virtual DbSet<Note> Notes { get; set; }

    public virtual DbSet<Marker> Markers { get; set; }

    public virtual DbSet<Tweet> Tweets { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        => optionsBuilder.UseSqlite("DataSource=file::memory:?cache=shared");

    //protected override void OnModelCreating(ModelBuilder modelBuilder)
    //{
    //    modelBuilder.Entity<Editor>(entity =>
    //    {
    //        entity.ToTable("Editor");

    //        entity.HasIndex(e => e.Login, "IX_Editor_login").IsUnique();

    //        entity.Property(e => e.Id)
    //            .ValueGeneratedNever()
    //            .HasColumnName("id");
    //        entity.Property(e => e.Firstname).HasColumnName("firstname");
    //        entity.Property(e => e.Lastname).HasColumnName("lastname");
    //        entity.Property(e => e.Login).HasColumnName("login");
    //        entity.Property(e => e.Password).HasColumnName("password");
    //    });

    //    modelBuilder.Entity<Post>(entity =>
    //    {
    //        entity.ToTable("Post");

    //        entity.Property(e => e.Id)
    //            .ValueGeneratedNever()
    //            .HasColumnName("id");
    //        entity.Property(e => e.Content).HasColumnName("content");
    //        entity.Property(e => e.TweetId).HasColumnName("tweetId");

    //        entity.HasOne(d => d.Tweet).WithMany(p => p.Posts).HasForeignKey(d => d.TweetId);
    //    });

    //    modelBuilder.Entity<Sticker>(entity =>
    //    {
    //        entity.ToTable("Sticker");

    //        entity.HasIndex(e => e.Id, "IX_Sticker_id").IsUnique();

    //        entity.HasIndex(e => e.Name, "IX_Sticker_name").IsUnique();

    //        entity.Property(e => e.Id)
    //            .ValueGeneratedNever()
    //            .HasColumnName("id");
    //        entity.Property(e => e.Name).HasColumnName("name");
    //    });

    //    modelBuilder.Entity<Tweet>(entity =>
    //    {
    //        entity.ToTable("Tweet");

    //        entity.HasIndex(e => e.Title, "IX_Tweet_title").IsUnique();

    //        entity.Property(e => e.Id)
    //            .ValueGeneratedNever()
    //            .HasColumnName("id");
    //        entity.Property(e => e.Content).HasColumnName("content");
    //        entity.Property(e => e.Created).HasColumnName("created");
    //        entity.Property(e => e.Modified).HasColumnName("modified");
    //        entity.Property(e => e.Title).HasColumnName("title");

    //        entity.HasOne(d => d.Editor).WithMany(p => p.Tweets).HasForeignKey(d => d.EditorId);
    //    });

    //    OnModelCreatingPartial(modelBuilder);
    //}

    //partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
