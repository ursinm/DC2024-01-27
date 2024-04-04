using DC.Models;

namespace DC.Repositories.Interfaces
{
	public interface IBaseRepository<T> where T : BaseModel
	{
		Task<IEnumerable<T>> GetAllAsync();

		Task<T?> GetByIdAsync(long id);

		Task<T> CreateAsync(T entity);

		Task<T> UpdateAsync(T entity);

		Task<bool> DeleteAsync(long id);
	}
}
