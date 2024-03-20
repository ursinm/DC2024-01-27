using AutoMapper;
using RV.Models;
using RV.Repositories;
using RV.Views;
using RV.Views.DTO;

namespace RV.Services.DataProviderServices.SQL
{
    public class SQLNewsDataProvider : INewsDataProvider
    {
        private IRepository<News> _repository;
        private IMapper _mapper;

        public SQLNewsDataProvider(IRepository<News> repository, IMapper mapper)
        {
            _repository = repository;
            _mapper = mapper;
        }

        public NewsDTO CreateNews(NewsAddDTO item)
        {
            item.created = DateTime.UtcNow;
            item.modified = DateTime.UtcNow;
            News n = _mapper.Map<News>(item);
            var res = _repository.Create(n);
            return _mapper.Map<NewsDTO>(res);
        }

        public int DeleteNews(int id)
        {
            int res = _repository.Delete(id);
            return res;
        }

        public NewsDTO GetNew(int id)
        {
            return _mapper.Map<NewsDTO>(_repository.Get(id));
        }

        public List<NewsDTO> GetNews()
        {
            List<NewsDTO> res = [];
            foreach (News n in _repository.GetAll())
            {
                res.Add(_mapper.Map<NewsDTO>(n));
            }
            return res;
        }

        public NewsDTO UpdateNews(NewsUpdateDTO item)
        {
            var n = _mapper.Map<News>(item);
            var res = _repository.Update(n);
            return _mapper.Map<NewsDTO>(res);
        }
    }
}
