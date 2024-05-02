using DC.Models;
using DC.Repositories.Interfaces;

namespace DC.Repositories.Implementations
{
	public class InMemoryLabelRepository : ILabelRepository
	{
		private readonly Dictionary<long, Label> _labels = new();
		private long _idCounter;
		public async Task<IEnumerable<Label>> GetAllAsync()
		{
			IEnumerable<Label> labels = Array.Empty<Label>();
			await Task.Run(() =>
			{
				labels = _labels.Values.ToList();
			});
			return labels;
		}

		public async Task<Label> GetByIdAsync(long id)
		{
			Label? result = null;
			await Task.Run(() =>
			{
				_labels.TryGetValue(id, out result);
			});
			return result;
		}

		public async Task<Label> CreateAsync(Label entity)
		{
			await Task.Run(() =>
			{
				var id = Interlocked.Increment(ref _idCounter);
				entity.Id = id;
				_labels.TryAdd(id, entity);
			});
			return entity;
		}

		public async Task<Label> UpdateAsync(Label entity)
		{
			await Task.Run(() =>
			{
				if (_labels.ContainsKey(entity.Id))
				{
					_labels[entity.Id] = entity; 
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
				result = _labels.Remove(id);
			});
			return result;
		}

	}
}
