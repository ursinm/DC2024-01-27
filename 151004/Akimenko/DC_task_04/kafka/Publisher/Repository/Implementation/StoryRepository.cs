using Publisher.Entity.Db;
using Publisher.Repository.Implementation.Common;
using Publisher.Repository.Interface;
using Publisher.Storage.Common;

namespace Publisher.Repository.Implementation
{
    public class storyRepository(DbStorage context) : AbstractCrudRepository<story>(context), IstoryRepository
    {
        private readonly DbStorage _context = context;

        public override story Add(story story)
        {
            var creator = new creator { Id = story.creatorId };
            _context.creators.Attach(creator);
            story.creator = creator;

            return base.Add(story);
        }

        public override async Task<story> AddAsync(story story)
        {
            var creator = new creator { Id = story.creatorId };
            _context.creators.Attach(creator);
            story.creator = creator;

            return await base.AddAsync(story);
        }
    }
}
