using DC.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace DC.EntityTypeConfigurations
{
	public class CreatorConfiguration : IEntityTypeConfiguration<Creator>
	{
		public void Configure(EntityTypeBuilder<Creator> builder)
		{
			builder.ToTable("tblCreator");
			builder.HasKey(creator => creator.Id);
			builder.HasIndex(creator => creator.Login).IsUnique();
			builder.Property(creator => creator.Login).HasMaxLength(64).IsRequired();
			builder.Property(creator => creator.Password).HasMaxLength(128).IsRequired();
			builder.Property(creator => creator.Firstname).HasMaxLength(64).IsRequired();
			builder.Property(creator => creator.Lastname).HasMaxLength(64).IsRequired();
		}
	}
}
