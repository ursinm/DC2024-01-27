using DC.Models;
using DC.Repositories.Interfaces;

namespace DC.Repositories.Implementations
{
	public class InMemoryStoryRepository : IStoryRepository
	{
		private readonly Dictionary<long, Story> _storys = new();
		private long _idCounter;

		public async Task<IEnumerable<Story>> GetAllAsync()
		{
			IEnumerable<Story> storys = Array.Empty<Story>();
			await Task.Run(() =>
			{
				storys = _storys.Values.ToList();
			});
			return storys;
		}

		public async Task<Story> GetByIdAsync(long id)
		{
			Story? result = null;
			await Task.Run(() =>
			{
				_storys.TryGetValue(id, out result);
			});
			return result;
		}

		public async Task<Story> CreateAsync(Story entity)
		{
			await Task.Run(() =>
			{
				var id = Interlocked.Increment(ref _idCounter);
				entity.Id = id;
				_storys.TryAdd(id, entity);
			});
			return entity;
		}

		public async Task<Story> UpdateAsync(Story entity)
		{
			await Task.Run(() =>
			{
				if (_storys.ContainsKey(entity.Id))
				{
					_storys[entity.Id] = entity; 
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
				result = _storys.Remove(id);
			});
			return result;
		}

	}
}
