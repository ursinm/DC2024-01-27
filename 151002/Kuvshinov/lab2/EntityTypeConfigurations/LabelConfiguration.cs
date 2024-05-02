using DC.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace DC.EntityTypeConfigurations
{
	public class LabelConfiguration : IEntityTypeConfiguration<Label>
	{
		public void Configure(EntityTypeBuilder<Label> builder)
		{
			builder.ToTable("tblLabel");
			builder.HasKey(label => label.Id);
			builder.HasIndex(label => label.Name).IsUnique();
			builder.Property(label => label.Name).HasMaxLength(32).IsRequired();
		}
	}
}
