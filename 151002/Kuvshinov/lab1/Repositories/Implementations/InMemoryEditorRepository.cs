using DC.Models;
using DC.Repositories.Interfaces;
using System.Collections.Concurrent;

namespace DC.Repositories.Implementations
{
	public class InMemoryEditorRepository : IEditorRepository
	{
		private readonly Dictionary<long, Editor> _editors = new();
		private long _idCounter;

		public async Task<IEnumerable<Editor>> GetAllAsync()
		{
			IEnumerable<Editor> editors = Array.Empty<Editor>();
			await Task.Run(() =>
			{
				editors = _editors.Values.ToList();
			});
			return editors;
		}

		public async Task<Editor> GetByIdAsync(long id)
		{
			Editor? result = null;
			await Task.Run(() =>
			{
				_editors.TryGetValue(id, out result);
			});
			return result;
		}

		public async Task<Editor> CreateAsync(Editor entity)
		{
			await Task.Run(() =>
			{
				var id = Interlocked.Increment(ref _idCounter);
				entity.Id = id;
				_editors.TryAdd(id, entity);
			});
			return entity;
		}

		public async Task<Editor> UpdateAsync(Editor entity)
		{
			await Task.Run(() =>
			{
				if (_editors.ContainsKey(entity.Id))
				{
					_editors[entity.Id] = entity;
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
				result = _editors.Remove(id);
			});
			return result;
		}
	}
}
