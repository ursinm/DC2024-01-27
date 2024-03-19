using DC.Models;
using DC.Repositories.Interfaces;

namespace DC.Repositories.Implementations
{
	public class InMemoryIssueRepository : IIssueRepository
	{
		private readonly Dictionary<long, Issue> _issues = [];
		private long _idCounter;

		public async Task<IEnumerable<Issue>> GetAllAsync()
		{
			IEnumerable<Issue> issues = [];
			await Task.Run(() =>
			{
				issues = _issues.Values.ToList();
			});
			return issues;
		}

		public async Task<Issue?> GetByIdAsync(long id)
		{
			Issue? result = null;
			await Task.Run(() =>
			{
				_issues.TryGetValue(id, out result);
			});
			return result;
		}

		public async Task<Issue> CreateAsync(Issue entity)
		{
			await Task.Run(() =>
			{
				var id = Interlocked.Increment(ref _idCounter);
				entity.Id = id;
				_issues.TryAdd(id, entity);
			});
			return entity;
		}

		public async Task<Issue> UpdateAsync(Issue entity)
		{
			await Task.Run(() =>
			{
				if (_issues.ContainsKey(entity.Id))
				{
					_issues[entity.Id] = entity; 
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
				result = _issues.Remove(id);
			});
			return result;
		}

	}
}
