using Api.Models;
using Api.Repositories.Interfaces;

namespace Api.Repositories.Implementations
{
    public class InMemoryCreatorRepository : ICreatorRepository
    {
        private readonly Dictionary<long, Creator> _creators = [];
        private long _idCounter;

        public async Task<IEnumerable<Creator>> GetAllAsync()
        {
            IEnumerable<Creator> creators = [];
            await Task.Run(() =>
            {
                creators = _creators.Values.ToList();
            });
            return creators;
        }

        public async Task<Creator?> GetByIdAsync(long id)
        {
            Creator? result = null;
            await Task.Run(() =>
            {
                _creators.TryGetValue(id, out result);
            });
            return result;
        }

        public async Task<Creator> CreateAsync(Creator entity)
        {
            await Task.Run(() =>
            {
                var id = Interlocked.Increment(ref _idCounter);
                entity.Id = id;
                _creators.TryAdd(id, entity);
            });
            return entity;
        }

        public async Task<Creator?> UpdateAsync(Creator entity)
        {
            await Task.Run(() =>
            {
                if (_creators.ContainsKey(entity.Id))
                {
                    _creators[entity.Id] = entity;
                }
                else
                {
                    entity = null;
                }
            });
            return entity;
        }

        public async Task<bool> DeleteAsync(long id)
        {
            bool result = true;
            await Task.Run(() =>
            {
                result = _creators.Remove(id);
            });
            return result;
        }
    }
}
