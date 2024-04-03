using DC.Data;
using DC.Exceptions;
using DC.Models;
using DC.Repositories.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace DC.Repositories.Implementations
{
	public abstract class BaseSqlRepository<T>(ApplicationDbContext dbContext) : IBaseRepository<T> where T : BaseModel
	{
		protected readonly ApplicationDbContext _dbContext = dbContext;
		protected readonly DbSet<T> _dbSet = dbContext.Set<T>();

		public async Task<IEnumerable<T>> GetAllAsync()
		{
			return await _dbSet.AsNoTracking().ToListAsync();
		}

		public async Task<T?> GetByIdAsync(long id)
		{
			return await _dbSet.AsNoTracking().SingleOrDefaultAsync(c => c.Id == id);
		}

		public async Task<T> CreateAsync(T entity)
		{
			_dbContext.Add(entity);
			try 
			{
				await _dbContext.SaveChangesAsync();
			}
			catch (DbUpdateException)
			{
				throw new AlreadyExistsException(ErrorMessages.AlreadyExistsMessage(entity.GetType()));
			}
			return entity;
		}

		public async Task<T> UpdateAsync(T entity)
		{
			_dbContext.Update(entity);
			try
			{
				await _dbContext.SaveChangesAsync();
			}
			catch (DbUpdateException)
			{
				throw new AlreadyExistsException(ErrorMessages.AlreadyExistsMessage(entity.GetType()));
			}
			return entity;
		}

		public async Task<bool> DeleteAsync(long id)
		{
			var entity = await GetByIdAsync(id);
			if (entity == null)
			{
				return false;
			}
			_dbContext.Remove(entity);
			await _dbContext.SaveChangesAsync();
			return true;
		}
	}
}
