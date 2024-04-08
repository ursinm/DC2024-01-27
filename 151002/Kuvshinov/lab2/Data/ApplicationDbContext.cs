using DC.EntityTypeConfigurations;
using DC.Models;
using Microsoft.EntityFrameworkCore;

namespace DC.Data
{
	public class ApplicationDbContext : DbContext
	{
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options) {}

        public DbSet<Editor> Creator { get; set; }

        public DbSet<Story> Storys { get; set; }

        public DbSet<Label> Labels { get; set; }

        public DbSet<Note> Notes { get; set; }

		protected override void OnModelCreating(ModelBuilder modelBuilder)
		{
			modelBuilder.Entity<Story>()
				.HasMany(story => story.Labels)
				.WithMany(label => label.Storys)
				.UsingEntity("tblStoryLabel");

			modelBuilder.ApplyConfiguration(new EditorConfiguration());
			modelBuilder.ApplyConfiguration(new StoryConfiguration());
			modelBuilder.ApplyConfiguration(new LabelConfiguration());
			modelBuilder.ApplyConfiguration(new NoteConfiguration());

			base.OnModelCreating(modelBuilder);
		}
	}
}
