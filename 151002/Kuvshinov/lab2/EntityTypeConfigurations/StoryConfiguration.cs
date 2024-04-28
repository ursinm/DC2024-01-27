using DC.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace DC.EntityTypeConfigurations
{
	public class StoryConfiguration : IEntityTypeConfiguration<Story>
	{
		public void Configure(EntityTypeBuilder<Story> builder)
		{
			builder.ToTable("tblStory");
			builder.HasKey(story => story.Id);
			builder.HasIndex(story => story.Title).IsUnique();
			builder.Property(story => story.Title).HasMaxLength(64).IsRequired();
			builder.Property(story => story.Content).HasMaxLength(2048).IsRequired();
		}
	}
}
