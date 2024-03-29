using DC.Models;
using DC.Repositories.Interfaces;

namespace DC.Repositories.Implementations
{
	public class InMemoryPostRepository : IPostRepository
	{
		private readonly Dictionary<long, Post> _posts = [];
		private long _idCounter;

		public async Task<IEnumerable<Post>> GetAllAsync()
		{
			IEnumerable<Post> posts = [];
			await Task.Run(() =>
			{
				posts = _posts.Values.ToList();
			});
			return posts;
		}

		public async Task<Post?> GetByIdAsync(long id)
		{
			Post? result = null;
			await Task.Run(() =>
			{
				_posts.TryGetValue(id, out result);
			});
			return result;
		}

		public async Task<Post> CreateAsync(Post entity)
		{
			await Task.Run(() =>
			{
				var id = Interlocked.Increment(ref _idCounter);
				entity.Id = id;
				_posts.TryAdd(id, entity);
			});
			return entity;
		}

		public async Task<Post> UpdateAsync(Post entity)
		{
			await Task.Run(() =>
			{
				if (_posts.ContainsKey(entity.Id))
				{
					_posts[entity.Id] = entity; 
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
				result = _posts.Remove(id);
			});
			return result;
		}
	}
}
