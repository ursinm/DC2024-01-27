using Api.Models;
using Api.Repositories.Interfaces;

namespace Api.Repositories.Implementations
{
    public class InMemoryNewsRepository : INewsRepository
    {
        private readonly Dictionary<long, News> _news = [];
        private long _idCounter;

        public async Task<IEnumerable<News>> GetAllAsync()
        {
            IEnumerable<News> issues = [];
            await Task.Run(() =>
            {
                issues = _news.Values.ToList();
            });
            return issues;
        }

        public async Task<News?> GetByIdAsync(long id)
        {
            News? result = null;
            await Task.Run(() =>
            {
                _news.TryGetValue(id, out result);
            });
            return result;
        }

        public async Task<News> CreateAsync(News entity)
        {
            await Task.Run(() =>
            {
                var id = Interlocked.Increment(ref _idCounter);
                entity.Id = id;
                _news.TryAdd(id, entity);
            });
            return entity;
        }

        public async Task<News?> UpdateAsync(News entity)
        {
            await Task.Run(() =>
            {
                if (_news.ContainsKey(entity.Id))
                {
                    _news[entity.Id] = entity;
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
                result = _news.Remove(id);
            });
            return result;
        }

    }
}
