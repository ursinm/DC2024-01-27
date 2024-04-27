using DC.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace DC.EntityTypeConfigurations
{
	public class EditorConfiguration : IEntityTypeConfiguration<Editor>
	{
		public void Configure(EntityTypeBuilder<Editor> builder)
		{
			builder.ToTable("tblEditor");
			builder.HasKey(editor => editor.Id);
			builder.HasIndex(editor => editor.Login).IsUnique();
			builder.Property(editor => editor.Login).HasMaxLength(64).IsRequired();
			builder.Property(editor => editor.Password).HasMaxLength(128).IsRequired();
			builder.Property(editor => editor.Firstname).HasMaxLength(64).IsRequired();
			builder.Property(editor => editor.Lastname).HasMaxLength(64).IsRequired();
		}
	}
}
