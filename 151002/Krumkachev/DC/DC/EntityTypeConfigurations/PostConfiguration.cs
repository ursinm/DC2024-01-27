using DC.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace DC.EntityTypeConfigurations
{
	public class PostConfiguration : IEntityTypeConfiguration<Post>
	{
		public void Configure(EntityTypeBuilder<Post> builder)
		{
			builder.ToTable("tblPost");
			builder.HasKey(post => post.Id);
			builder.Property(post => post.Content).HasMaxLength(2048).IsRequired();	
		}
	}
}
