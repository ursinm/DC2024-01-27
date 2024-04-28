using DC.Models;
using DC.Repositories.Interfaces;

namespace DC.Repositories.Implementations
{
	public class InMemoryNoteRepository : INoteRepository
	{
		private readonly Dictionary<long, Note> _notes = new();
		private long _idCounter;

		public async Task<IEnumerable<Note>> GetAllAsync()
		{
			IEnumerable<Note> notes = Array.Empty<Note>();
			await Task.Run(() =>
			{
				notes = _notes.Values.ToList();
			});
			return notes;
		}

		public async Task<Note> GetByIdAsync(long id)
		{
			Note? result = null;
			await Task.Run(() =>
			{
				_notes.TryGetValue(id, out result);
			});
			return result;
		}

		public async Task<Note> CreateAsync(Note entity)
		{
			await Task.Run(() =>
			{
				var id = Interlocked.Increment(ref _idCounter);
				entity.Id = id;
				_notes.TryAdd(id, entity);
			});
			return entity;
		}

		public async Task<Note> UpdateAsync(Note entity)
		{
			await Task.Run(() =>
			{
				if (_notes.ContainsKey(entity.Id))
				{
					_notes[entity.Id] = entity; 
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
				result = _notes.Remove(id);
			});
			return result;
		}
	}
}
