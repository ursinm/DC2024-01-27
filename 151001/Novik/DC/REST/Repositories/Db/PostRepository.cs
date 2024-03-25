using Microsoft.EntityFrameworkCore;
using REST.Models.Entity;
using REST.Repositories.interfaces;

namespace REST.Repositories.Db;

public class PostRepository :  IPostRepository
{
    private readonly AppDbContext _context;

    public PostRepository(AppDbContext context)
    {
        _context = context;
    }
    public async Task<Post?> GetByIdAsync(long id)
    {
        return await _context.Posts.FirstOrDefaultAsync(label => label.id == id);
    }

    public async Task<IEnumerable<Post>> GetAllAsync()
    {
        return await _context.Posts.ToListAsync();
    }

    public async Task<Post> AddAsync(Post entity)
    {
        var entityEntry = await _context.Posts.AddAsync(entity);
        await _context.SaveChangesAsync();
        return entityEntry.Entity;
    }

    public async Task<Post> UpdateAsync(Post entity)
    {
        var entityEntry = _context.Posts.Update(entity);
        await _context.SaveChangesAsync(); // Сохранить изменения в базе данных
        return entityEntry.Entity;
    }

    public async Task<bool> Exists(long id)
    {
        return _context.Posts.AsNoTracking().Any(label => label.id == id);
    }

    public async Task DeleteAsync(long id)
    {
        var entity = await _context.Posts.FindAsync(id);
        if (entity != null)
        {
            _context.Posts.Remove(entity);
            await _context.SaveChangesAsync();
        }
    }
}