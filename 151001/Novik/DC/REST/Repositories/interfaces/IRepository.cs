using System.Text.Json.Serialization.Metadata;

namespace REST.Repositories.interfaces;

public interface IRepository<TEntity>
{
    Task<TEntity?> GetByIdAsync(long id);
    
    Task<IEnumerable<TEntity>> GetAllAsync();
    
    Task<TEntity> AddAsync(TEntity entity);
    
    Task<TEntity> UpdateAsync(TEntity entity);

    Task<bool> Exists(long id);
    Task DeleteAsync(long id);

    private const String VladLox = "Влад лох";
}