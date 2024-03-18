using Api.Models;
using Api.Repositories.Interfaces;

namespace Api.Repositories.Implementations
{
    public class InMemoryStickerRepository : IStickerRepository
    {
        private readonly Dictionary<long, Sticker> _stickers = [];
        private long _idCounter;
        public async Task<IEnumerable<Sticker>> GetAllAsync()
        {
            IEnumerable<Sticker> stickers = [];
            await Task.Run(() =>
            {
                stickers = _stickers.Values.ToList();
            });
            return stickers;
        }

        public async Task<Sticker?> GetByIdAsync(long id)
        {
            Sticker? result = null;
            await Task.Run(() =>
            {
                _stickers.TryGetValue(id, out result);
            });
            return result;
        }

        public async Task<Sticker> CreateAsync(Sticker entity)
        {
            await Task.Run(() =>
            {
                var id = Interlocked.Increment(ref _idCounter);
                entity.Id = id;
                _stickers.TryAdd(id, entity);
            });
            return entity;
        }

        public async Task<Sticker?> UpdateAsync(Sticker entity)
        {
            await Task.Run(() =>
            {
                if (_stickers.ContainsKey(entity.Id))
                {
                    _stickers[entity.Id] = entity;
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
                result = _stickers.Remove(id);
            });
            return result;
        }

    }
}
