using DC.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace DC.EntityTypeConfigurations
{
	public class NoteConfiguration : IEntityTypeConfiguration<Note>
	{
		public void Configure(EntityTypeBuilder<Note> builder)
		{
			builder.ToTable("tblNote");
			builder.HasKey(note => note.Id);
			builder.Property(note => note.Content).HasMaxLength(2048).IsRequired();	
		}
	}
}
