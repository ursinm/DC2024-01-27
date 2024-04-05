using DC.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace DC.EntityTypeConfigurations
{
	public class IssueConfiguration : IEntityTypeConfiguration<Issue>
	{
		public void Configure(EntityTypeBuilder<Issue> builder)
		{
			builder.ToTable("tblIssue");
			builder.HasKey(issue => issue.Id);
			builder.HasIndex(issue => issue.Title).IsUnique();
			builder.Property(issue => issue.Title).HasMaxLength(64).IsRequired();
			builder.Property(issue => issue.Content).HasMaxLength(2048).IsRequired();
		}
	}
}
