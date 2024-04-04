namespace DC_REST.Repositories
{
	public interface IRepository<TEntity>
	{
		TEntity GetById(int id);
		IEnumerable<TEntity> GetAll();
		TEntity Add(TEntity entity);
		TEntity Update(int id, TEntity entity);
		bool Delete(int id);
		int GetCurrentId();
	}
}
