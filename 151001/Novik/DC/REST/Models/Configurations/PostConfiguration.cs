using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using REST.Models.Entity;

namespace REST.Models.Configurations;

public class PostConfiguration : IEntityTypeConfiguration<Post>
{
    public void Configure(EntityTypeBuilder<Post> entity)
    {
        entity.HasKey(e => e.id);
        entity.Property(e => e.id)
            .UseIdentityColumn()
            .IsRequired()
            .ValueGeneratedOnAdd();

        entity.HasIndex(e => e.newsId);
        
        entity.Property(e => e.content)
            .HasMaxLength(2048);
        
        entity
            .HasOne(p => p.News)
            .WithMany(n => n.Posts)
            .HasForeignKey(p => p.newsId);
    }
}