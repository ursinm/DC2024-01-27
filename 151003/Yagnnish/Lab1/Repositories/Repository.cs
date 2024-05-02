using lab_1.Domain;
using Microsoft.AspNetCore.Authentication.OAuth.Claims;
using System.Diagnostics.Eventing.Reader;

namespace lab_1.Repositories
{
    public class Repository<T>
    {
        private Dictionary<long?, T> _Dict;

        public long NextId { get => _Dict.Count; }
        public Repository() { _Dict = new(); }
        public void AddValue(T value)
        {
            _Dict.Add(NextId, value);
        }

        public bool DeleteValue(long id)
        {
            return _Dict.Remove(id);
        }

        public T FindById(long? id)
        {
            T res;
            try
            {
                res = _Dict[id];
            }
            catch (KeyNotFoundException)
            {
                Console.Error.WriteLine("Could not find");
                res = default(T);
            }
            return res;
        }

        public void UpdateValue(T entity, long? id)
        {
            _Dict[id] = entity;
        }

        public IEnumerable<T> GetAuthors() => _Dict.Values;

    }
}
