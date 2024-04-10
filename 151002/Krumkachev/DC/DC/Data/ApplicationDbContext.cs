using DC.EntityTypeConfigurations;
using DC.Models;
using Microsoft.EntityFrameworkCore;

namespace DC.Data
{
	public class ApplicationDbContext : DbContext
	{
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options) {}

        public DbSet<Creator> Creators { get; set; }

        public DbSet<Issue> Issues { get; set; }

        public DbSet<Label> Labels { get; set; }

        public DbSet<Post> Posts { get; set; }

		protected override void OnModelCreating(ModelBuilder modelBuilder)
		{
			modelBuilder.Entity<Issue>()
				.HasMany(issue => issue.Labels)
				.WithMany(label => label.Issues)
				.UsingEntity("tblIssueLabel");

			modelBuilder.ApplyConfiguration(new CreatorConfiguration());
			modelBuilder.ApplyConfiguration(new IssueConfiguration());
			modelBuilder.ApplyConfiguration(new LabelConfiguration());
			modelBuilder.ApplyConfiguration(new PostConfiguration());

			base.OnModelCreating(modelBuilder);
		}
	}
}
